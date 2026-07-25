package sg.com.chen.Beginner349.app.service.s3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.com.chen.Beginner349.app.entity.s3.Upload;
import sg.com.chen.Beginner349.app.entity.s3.UploadStatus;
import sg.com.chen.Beginner349.app.model.s3.*;
import sg.com.chen.Beginner349.app.repository.s3.UploadRepository;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.Part;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.UploadPartPresignRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MultipartUploadService {
    private static final Logger log = LoggerFactory.getLogger(MultipartUploadService.class);

    private static final long MIN_PART_SIZE = 8L * 1024 * 1024;   // 8 MiB
    private static final int MAX_PARTS = 10_000;
    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024 * 1024 * 1024; // 5 TiB

    private final S3Client s3;
    private final S3Presigner presigner;
    private final String bucket;
    private final UploadRepository uploadRepository;

    public MultipartUploadService(S3Client s3, S3Presigner presigner, UploadRepository uploadRepository, @Value("${app.s3.bucket}") String bucket) {
        this.s3 = s3;
        this.presigner = presigner;
        this.uploadRepository = uploadRepository;
        this.bucket = bucket;
    }

    @Transactional
    public InitiateUploadResponse initiate(InitiateUploadRequest req) {
        log.info("Initiating upload for file: {}, size: {} bytes", req.fileName(), req.fileSize());

        if (req.fileSize() <= 0 || req.fileSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size must be between 1 byte and 5 TiB");
        }

        long partSize = Math.max(MIN_PART_SIZE, ceilDiv(req.fileSize(), MAX_PARTS));
        int partCount = (int) ceilDiv(req.fileSize(), partSize);

        var key = "testdata/csv/%s-%s".formatted(UUID.randomUUID(), req.fileName());

        var created = s3.createMultipartUpload(b -> b
                .bucket(bucket)
                .key(key)
                .contentType(req.contentType()));

        Upload upload = new Upload(
                req.fileName(),
                req.fileSize(),
                partSize,
                partCount,
                req.contentType(),
                key,
                created.uploadId(),
                UploadStatus.INITIATED,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Upload saved = uploadRepository.save(upload);

        log.info("Upload initiated successfully, id: {}, file: {}, part size: {}, bytes part count: {}", saved.id.toString(), req.fileName(), partSize, partCount);
        return new InitiateUploadResponse(saved.id.toString(), partSize, partCount);
    }

    public UploadStatusResponse getStatus(String id) {
        log.info("Retrieving status for upload: {}", id);

        Upload upload = findOrThrow(id);
        List<Integer> uploaded = UploadStatus.INITIATED.equals(upload.status) ? listUploadedPartNumbers(upload) : List.of();

        log.info("Upload {} status: {}, uploaded parts: {}/{}", id, upload.status, uploaded.size(), upload.totalParts);

        return new UploadStatusResponse(upload.status, upload.partSize, upload.totalParts, uploaded);
    }

    private List<Integer> listUploadedPartNumbers(Upload upload) {
        log.info("Listing uploaded part numbers for upload: {}", upload.id);
        return s3.listPartsPaginator(b -> b
                        .bucket(bucket)
                        .key(upload.s3Key)
                        .uploadId(upload.s3UploadId))
                .stream()
                .flatMap(page -> page.parts().stream())
                .map(Part::partNumber)
                .toList();
    }

    public List<PartUrl> retrievePresignedUrls(String id, GetPresignedUrlRequest req) {
        log.info("Retrieving presigned URLs for upload: {}, parts: {}", id, req.partNumbers());

        Upload upload = findOrThrow(id);
        if (!UploadStatus.INITIATED.equals(upload.status)) throw new IllegalStateException("Upload is not in progress");
        if (req.partNumbers().stream().anyMatch(i -> i < 1 || i > upload.totalParts))
            throw new IllegalArgumentException("Invalid part number(s)");
        List<Integer> partNumberList = req.partNumbers();
        return partNumberList
                .stream()
                .map(i -> new PartUrl(i, presignPart(upload.s3Key, upload.s3UploadId, i)))
                .toList();
    }

    @Transactional
    public void complete(String id) {
        log.info("Completing upload: {}", id);

        Upload upload = findOrThrow(id);
        if (UploadStatus.COMPLETED.equals(upload.status)) return;
        if (UploadStatus.ABORTED.equals(upload.status)) throw new IllegalStateException("Upload was aborted");

        List<CompletedPart> parts = s3.listPartsPaginator(b -> b
                        .bucket(bucket)
                        .key(upload.s3Key)
                        .uploadId(upload.s3UploadId)
                )
                .stream()
                .flatMap(page -> page.parts().stream())
                .map(p -> CompletedPart.builder().partNumber(p.partNumber()).eTag(p.eTag()).build())
                .toList();

        if (parts.size() != upload.totalParts)
            throw new IllegalStateException("Only %d of %d parts uploaded"
                    .formatted(parts.size(), upload.totalParts));

        s3.completeMultipartUpload(b -> b
                .bucket(bucket)
                .key(upload.s3Key)
                .uploadId(upload.s3UploadId)
                .multipartUpload(mp -> mp.parts(parts))
        );
        upload.status = UploadStatus.COMPLETED;
        upload.updatedAt = LocalDateTime.now();
        log.info("Upload {} completed successfully with {} parts", id, parts.size());
    }

    private String presignPart(String key, String uploadId, int partNumber) {
        var uploadPart = UploadPartRequest.builder()
                .bucket(bucket)
                .key(key)
                .uploadId(uploadId)
                .partNumber(partNumber)
                .build();

        var presignReq = UploadPartPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(15))
                .uploadPartRequest(uploadPart)
                .build();

        return presigner.presignUploadPart(presignReq).url().toString();
    }

    @Transactional
    public void abort(String id) {
        log.info("Aborting upload: {}", id);

        Upload upload = findOrThrow(id);
        if (UploadStatus.COMPLETED.equals(upload.status)) throw new IllegalStateException("Upload already completed");
        if (!UploadStatus.ABORTED.equals(upload.status)) {
            s3.abortMultipartUpload(b -> b
                    .bucket(bucket)
                    .key(upload.s3Key)
                    .uploadId(upload.s3UploadId)
            );
            upload.status = UploadStatus.ABORTED;
            upload.updatedAt = LocalDateTime.now();
            log.info("Upload {} aborted successfully", id);
        }
    }

    private long ceilDiv(long a, long b) {
        return (a + b - 1) / b;
    }

    private Upload findOrThrow(String uploadId) {
        return uploadRepository.findById(UUID.fromString(uploadId)).orElseThrow(() -> new IllegalArgumentException("Upload not found"));
    }
}

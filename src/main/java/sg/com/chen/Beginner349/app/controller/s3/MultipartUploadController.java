package sg.com.chen.Beginner349.app.controller.s3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.com.chen.Beginner349.app.model.s3.*;
import sg.com.chen.Beginner349.app.service.s3.MultipartUploadService;

import java.util.List;

/**
 * Controller for handling S3 multipart upload operations.
 */
@RestController
@RequestMapping("/api/multipart-uploads")
public class MultipartUploadController {
    private final MultipartUploadService multipartUploadService;

    public MultipartUploadController(MultipartUploadService multipartUploadService) {
        this.multipartUploadService = multipartUploadService;
    }

    @PostMapping("/initiate")
    public InitiateUploadResponse initiate(@RequestBody InitiateUploadRequest req) {
        return multipartUploadService.initiate(req);
    }

    @PostMapping("/{id}/part-urls")
    public List<PartUrl> retrievePresignedUrls(@PathVariable String id, @RequestBody GetPresignedUrlRequest req) {
        return multipartUploadService.retrievePresignedUrls(id, req);
    }

    @GetMapping("/{id}/status")
    public UploadStatusResponse getStatus(@PathVariable String id) {
        return multipartUploadService.getStatus(id);
    }

    @PutMapping("/{id}/complete")
    public String complete(@PathVariable String id) {
        multipartUploadService.complete(id);
        return "Upload completed successfully";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> abort(@PathVariable String id) {
        multipartUploadService.abort(id);
        return ResponseEntity.noContent().build();
    }
}

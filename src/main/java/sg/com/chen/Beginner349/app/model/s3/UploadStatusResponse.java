package sg.com.chen.Beginner349.app.model.s3;

import sg.com.chen.Beginner349.app.entity.s3.UploadStatus;

import java.util.List;

public record UploadStatusResponse(
        UploadStatus status,
        long partSize,
        int partCount,
        List<Integer> uploadedParts
) {
}

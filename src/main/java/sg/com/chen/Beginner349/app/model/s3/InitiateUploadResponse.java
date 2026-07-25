package sg.com.chen.Beginner349.app.model.s3;

public record InitiateUploadResponse(String uploadId, long partSize, int partCount) {
}

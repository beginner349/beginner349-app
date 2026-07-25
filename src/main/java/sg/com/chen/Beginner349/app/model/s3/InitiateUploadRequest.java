package sg.com.chen.Beginner349.app.model.s3;

public record InitiateUploadRequest(String fileName, long fileSize, String contentType) {
}

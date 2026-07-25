package sg.com.chen.Beginner349.app.entity.s3;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "upload")
public class Upload {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    public UUID id;

    @Column(name = "file_name")
    public String fileName;

    @Column(name = "file_size")
    public Long fileSize;

    @Column(name = "part_size")
    public long partSize;

    @Column(name = "total_parts")
    public int totalParts;

    @Column(name = "content_type")
    public String contentType;

    @Column(name = "s3_key")
    public String s3Key;

    @Column(name = "s3_upload_id")
    public String s3UploadId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    public UploadStatus status;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    public Upload() {
    }

    public Upload(String fileName, Long fileSize, Long partSize, int totalParts, String contentType, String s3Key, String s3UploadId, UploadStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.partSize = partSize;
        this.totalParts = totalParts;
        this.contentType = contentType;
        this.s3Key = s3Key;
        this.s3UploadId = s3UploadId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

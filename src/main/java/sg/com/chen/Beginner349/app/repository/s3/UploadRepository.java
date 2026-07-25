package sg.com.chen.Beginner349.app.repository.s3;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.com.chen.Beginner349.app.entity.s3.Upload;

import java.util.UUID;

public interface UploadRepository extends JpaRepository<Upload, UUID> {
}

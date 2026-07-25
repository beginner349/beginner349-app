package sg.com.chen.Beginner349.app.model.s3;

import java.util.List;

public record GetPresignedUrlRequest(List<Integer> partNumbers) {
}

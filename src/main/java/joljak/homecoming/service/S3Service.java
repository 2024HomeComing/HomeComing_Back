package joljak.homecoming.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;


@Service
public class S3Service {
    private final AmazonS3 amazonS3;

    @Autowired
    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(String key, byte[] content) throws IOException {
        String bucketName = "homecoming-image"; // 실제 사용 중인 S3 버킷 이름으로 교체하세요.

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(content.length);

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content)) {
            amazonS3.putObject(bucketName, key, byteArrayInputStream, metadata);
        }
        System.out.println("사진 업로드 됨.");
        return amazonS3.getUrl(bucketName, key).toString();
    }

}

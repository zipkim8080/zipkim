package com.kb.zipkim.domain.prop.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
//@Profile("prod")
@RequiredArgsConstructor
public class FileStoreServiceAWS extends FileStoreService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3 s3Client;

    @Override
    public String getFullPath(String filename) {
        URL url = s3Client.getUrl(bucketName, filename);
        return url.toString();
    }


    @Override
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);

        // S3에 파일업로드
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());

        s3Client.putObject(bucketName, storeFileName, multipartFile.getInputStream(), metadata);
        return new UploadFile(originalFilename, storeFileName);
    }
}

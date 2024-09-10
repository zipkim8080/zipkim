package com.kb.zipkim.domain.prop.file;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Profile("prod")
public class FileStoreServiceAWS extends FileStoreService {
    @Override
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        return List.of();
    }

    @Override
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        return null;
    }
}

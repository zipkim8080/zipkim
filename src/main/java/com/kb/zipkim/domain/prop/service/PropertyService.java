package com.kb.zipkim.domain.prop.service;

import com.kb.zipkim.domain.prop.dto.PropRegisterForm;
import com.kb.zipkim.domain.prop.dto.RegisterResult;
import com.kb.zipkim.domain.prop.entity.Property;
import com.kb.zipkim.domain.prop.file.FileStoreService;
import com.kb.zipkim.domain.prop.file.UploadFile;
import com.kb.zipkim.domain.prop.repository.PropertyRepository;
import com.kb.zipkim.domain.register.Registered;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final FileStoreService fileStoreService;

    @Transactional
    public RegisterResult registerProp(PropRegisterForm form) throws IOException {
        List<UploadFile> uploadFiles = fileStoreService.storeFiles(form.getImages());

        Property property = Property.makeProperty(form);
        Registered registered = Registered.builder()
                .registerUniqueNum(form.getRegisterUniqueNum())
                .build();
        property.register(registered);
        property.upload(uploadFiles);
        propertyRepository.save(property);
        RegisterResult result = new RegisterResult();
        for (UploadFile uploadFile : uploadFiles) {
            result.getImageUrl().add(fileStoreService.getFullPath(uploadFile.getStoreFileName()));
        }
        result.setPropId(property.getId());
        return result;
    }

}

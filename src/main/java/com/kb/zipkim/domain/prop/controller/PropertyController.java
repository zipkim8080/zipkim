package com.kb.zipkim.domain.prop.controller;

import com.kb.zipkim.domain.prop.dto.PropRegisterForm;
import com.kb.zipkim.domain.prop.dto.RegisterResult;
import com.kb.zipkim.domain.prop.file.FileStoreService;
import com.kb.zipkim.domain.prop.file.UploadFile;
import com.kb.zipkim.domain.prop.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PropertyController {

    private final PropertyService propertyService;
    private final FileStoreService fileStoreService;

    @PostMapping("/api/property")
    public RegisterResult createProp(@ModelAttribute PropRegisterForm propRegisterForm) throws IOException {
        return propertyService.registerProp(propRegisterForm);
    }

}

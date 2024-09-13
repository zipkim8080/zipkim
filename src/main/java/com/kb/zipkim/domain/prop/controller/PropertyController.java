package com.kb.zipkim.domain.prop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kb.zipkim.domain.prop.dto.LocationWithRadius;
import com.kb.zipkim.domain.prop.dto.NearByComplex;
import com.kb.zipkim.domain.prop.dto.PropRegisterForm;
import com.kb.zipkim.domain.prop.dto.RegisterResult;
import com.kb.zipkim.domain.prop.file.FileStoreService;
import com.kb.zipkim.domain.prop.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/map/{type}")
    public ResponseEntity<List<NearByComplex>> getNearComplexes(@PathVariable String type, @ModelAttribute LocationWithRadius locationWithRadius) throws JsonProcessingException {
        List<NearByComplex> props = propertyService.findNearProp(type, locationWithRadius.getLat(), locationWithRadius.getLon(), locationWithRadius.getRadius());
        return ResponseEntity.ok().body(props);
    }
}

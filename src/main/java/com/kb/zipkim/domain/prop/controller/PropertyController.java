package com.kb.zipkim.domain.prop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kb.zipkim.domain.prop.ComplexListResponse;
import com.kb.zipkim.domain.prop.dto.*;
import com.kb.zipkim.domain.prop.file.FileStoreService;
import com.kb.zipkim.domain.prop.repository.ComplexQueryRepository;
import com.kb.zipkim.domain.prop.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PropertyController {

    private final PropertyService propertyService;
    private final ComplexQueryRepository complexQueryRepository;

    @PostMapping("/api/property")
    public RegisterResult createProp(@ModelAttribute PropRegisterForm propRegisterForm) throws IOException {
        return propertyService.registerProp(propRegisterForm);
    }

    @GetMapping("/api/map/{type}")
    public ComplexListResponse<List<NearByComplex>> getNearComplexes(@PathVariable String type, @ModelAttribute LocationWithRadius locationWithRadius) throws JsonProcessingException {
        List<NearByComplex> complexes = propertyService.findNearComplexes(type, locationWithRadius.getLat(), locationWithRadius.getLon(), locationWithRadius.getRadius());
        return ComplexListResponse.success(complexes);
    }

    @GetMapping("/api/search")
    public Page<SearchResponse> searchComplexes(@RequestParam String keyword, Pageable pageable) {
        return complexQueryRepository.search(keyword, pageable);
    }
}

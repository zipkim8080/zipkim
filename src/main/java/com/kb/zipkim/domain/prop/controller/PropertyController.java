package com.kb.zipkim.domain.prop.controller;

import com.amazonaws.services.kms.model.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kb.zipkim.domain.prop.ComplexListResponse;
import com.kb.zipkim.domain.prop.dto.*;
import com.kb.zipkim.domain.prop.entity.Complex;
import com.kb.zipkim.domain.prop.repository.ComplexPropQueryRepository;
import com.kb.zipkim.domain.prop.repository.ComplexRepository;
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
    private final ComplexRepository complexRepository;
    private final ComplexPropQueryRepository complexPropQueryRepository;

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
        return complexPropQueryRepository.search(keyword.trim(), pageable);
    }

    @GetMapping("/api/complex/summary")
    public ComplexInfo getComplexSummary(@RequestParam Long complexId) {
        Complex findComplex = complexRepository.findById(complexId)
                .orElseThrow(() -> new NotFoundException("해당 단지정보가 없습니다 단지Id: " + complexId));
        return new ComplexInfo(findComplex.getId(), findComplex.getBgdCd(), findComplex.getComplexName(), findComplex.getRoadName(), findComplex.getRecentAmount(), findComplex.getRecentDeposit(), findComplex.getAddressName(), findComplex.getMainAddressNo(), findComplex.getSubAddressNo());
    }

    @GetMapping("/api/complex/prop-list")
    public Page<SimplePropInfo> PropList(@RequestParam Long complexId, Pageable pageable) {
        return propertyService.findPropList(complexId, pageable);
    }
}

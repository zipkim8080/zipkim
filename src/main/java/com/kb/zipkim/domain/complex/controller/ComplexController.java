package com.kb.zipkim.domain.complex.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kb.zipkim.domain.complex.service.ComplexService;
import com.kb.zipkim.domain.prop.Response;
import com.kb.zipkim.domain.complex.dto.ComplexInfo;
import com.kb.zipkim.domain.complex.dto.LocationWithRadius;
import com.kb.zipkim.domain.complex.dto.NearByComplex;
import com.kb.zipkim.domain.complex.dto.SearchResponse;
import com.kb.zipkim.domain.prop.repository.ComplexPropQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ComplexController {


    private final ComplexService complexService;
    private final ComplexPropQueryRepository complexPropQueryRepository;

    @GetMapping("/api/map/{type}")
    public Response<List<NearByComplex>> getNearComplexes(@PathVariable String type, @ModelAttribute LocationWithRadius locationWithRadius) throws JsonProcessingException {
        List<NearByComplex> complexes = complexService.findNearComplexes(type, locationWithRadius.getLat(), locationWithRadius.getLon(), locationWithRadius.getRadius());
        return Response.success(complexes);
    }

    //테스트메서드
    @GetMapping("/api/v2/map/{type}")
    public Response<List<NearByComplex>> getNearComplexesV2(@PathVariable String type, @ModelAttribute LocationWithRadius locationWithRadius) throws JsonProcessingException {
        List<NearByComplex> complexes = complexService.findNearComplexesV2(type, locationWithRadius.getLat(), locationWithRadius.getLon(), locationWithRadius.getRadius());
        return Response.success(complexes);
    }

    @GetMapping("/api/complex/summary")
    public ComplexInfo getComplexSummary(@RequestParam Long complexId) {
        return complexService.findWithArea(complexId);
    }

    @GetMapping("/api/search")
    public Page<SearchResponse> searchComplexes(@RequestParam String keyword, Pageable pageable) {
        return complexPropQueryRepository.search(keyword.trim(), pageable);
    }

    @GetMapping("/api/select-info")
    public List<SearchResponse> getComplexes(@RequestParam String bgdCd) {
        return complexService.findByBgd(bgdCd);
    }
}

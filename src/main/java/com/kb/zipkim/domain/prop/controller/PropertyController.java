package com.kb.zipkim.domain.prop.controller;

import com.kb.zipkim.domain.bookMark.entity.BookMark;
import com.kb.zipkim.domain.bookMark.repository.BookMarkRepository;
import com.kb.zipkim.domain.login.dto.CustomOAuth2User;
import com.kb.zipkim.domain.prop.dto.*;
import com.kb.zipkim.domain.complex.entity.Complex;
import com.kb.zipkim.domain.prop.entity.Property;
import com.kb.zipkim.domain.prop.repository.ComplexPropQueryRepository;
import com.kb.zipkim.domain.complex.repository.ComplexRepository;
import com.kb.zipkim.domain.prop.repository.PropertyRepository;
import com.kb.zipkim.domain.prop.service.PropertyService;
import com.kb.zipkim.global.exception.AuthException;
import com.kb.zipkim.global.exception.ExceptionCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PropertyController {

    private final PropertyService propertyService;
    private final ComplexRepository complexRepository;
    private final ComplexPropQueryRepository complexPropQueryRepository;
    private final BookMarkRepository bookMarkRepository;
    private final PropertyRepository propertyRepository;

    @PostMapping("/api/property")
    public RegisterResult createProp(@Valid @ModelAttribute PropRegisterForm propRegisterForm, @AuthenticationPrincipal CustomOAuth2User user) throws IOException {
        if(user == null) throw new AuthException(ExceptionCode.WITHOUT_TOKEN);
        return propertyService.registerProp(propRegisterForm, user.getUsername());
    }


    @GetMapping("/api/prop-list")
    public Page<SimplePropInfo> PropList(@RequestParam Long complexId,@AuthenticationPrincipal CustomOAuth2User user, Pageable pageable) {
        return propertyService.findPropList(complexId,user, pageable);
    }

    @GetMapping("/api/prop/{id}")
    public DetailPropInfo getProp(@PathVariable Long id) {
        return propertyService.findPropById(id);
    }

    @GetMapping("/api/myprops/{username}")
    public Page<SimplePropInfo> getPropertiesByBrokerId(@PathVariable String username, Pageable pageable) {
        return propertyService.getPropertiesByBrokerId(username, pageable);
    }

    @Transactional
    @PostMapping("/api/delete/prop/{id}")
    public ResponseEntity deleteProp(@PathVariable Long id) {
        propertyService.deleteProp(id);
        return ResponseEntity.ok().build();
    }

}

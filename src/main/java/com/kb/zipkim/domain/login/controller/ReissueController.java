package com.kb.zipkim.domain.login.controller;

import com.kb.zipkim.domain.login.repository.RefreshRepository;
import com.kb.zipkim.domain.login.service.ReissueService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueController {

    private final ReissueService reissueService;

    private final RefreshRepository refreshRepository;

    @PostMapping("/api/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        // 서비스 메서드 호출
        return reissueService.reissueToken(request, response);
    }
}
package com.kb.zipkim.domain.login.controller;

import com.kb.zipkim.domain.login.entity.RefreshEntity;
import com.kb.zipkim.domain.login.jwt.JWTUtil;
import com.kb.zipkim.domain.login.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReissueController {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @PostMapping("/api/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String refresh = null;
        String bearerToken = request.getHeader("Refresh");
        log.info("Refresh 확인: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            refresh = bearerToken.substring(7);
        }
        log.info("Refresh: {}", refresh);

        if (refresh == null) {

            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            return new ResponseEntity<>("refresh token invalid", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refresh);
        String name = jwtUtil.getName(refresh);
        String role = jwtUtil.getRole(refresh);
        String email = jwtUtil.getEmail(refresh);

        refreshRepository.deleteByUsername(username);

        // 새 jwt 발급
        String newAccess = jwtUtil.createJwt("access", username, name, role, email,1800000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, name, role, email,7200000L);

        addRefreshEntity(username, newRefresh, 7200000L);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access", newAccess);
        tokens.put("refresh", newRefresh);

        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = RefreshEntity.builder()
                .refresh(refresh)
                .username(username)
                .expiration(date.toString())
                .build();

        refreshRepository.save(refreshEntity);
    }
}

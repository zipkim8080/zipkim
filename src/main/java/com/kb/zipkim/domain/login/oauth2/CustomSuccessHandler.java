package com.kb.zipkim.domain.login.oauth2;

import com.fasterxml.jackson.databind.DatabindException;
import com.kb.zipkim.domain.login.dto.CustomOAuth2User;
import com.kb.zipkim.domain.login.entity.RefreshEntity;
import com.kb.zipkim.domain.login.jwt.JWTUtil;
import com.kb.zipkim.domain.login.repository.RefreshRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Value("${front.url}")
    private String front_url;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        String email = customUserDetails.getEmail();
        String role = customUserDetails.getRole();
        String name = customUserDetails.getName();

        String token = jwtUtil.createJwt("access", username, name, role, email, 1800000L);     // Ms
        String refresh = jwtUtil.createJwt("refresh", username, name, role, email,7200000L);     // Ms

        addRefreshEntity(username, refresh, 7200000L);
        String host = request.getHeader("Referer");
        log.info("로그인성공공: {}",host);
//        getRedirectStrategy().sendRedirect(request, response, host+"redirect-uri?token="+token+"&refresh="+refresh);
        getRedirectStrategy().sendRedirect(request, response, front_url + "redirect-uri?token=" + token + "&refresh=" + refresh);
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = RefreshEntity.builder()
                .refresh(refresh)
                .username(username)
                .expiration(date.toString())
                .build();
//        RefreshEntity refreshEntity = new RefreshEntity();
//        refreshEntity.setRefresh(refresh);
//        refreshEntity.setUsername(username);
//        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}

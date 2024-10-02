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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public CustomSuccessHandler(JWTUtil jwtUtil, RefreshRepository refreshRepository) {

        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

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

//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority auth = iterator.next();
//        response.addCookie(createCookie("Authorization", token));
//        response.setHeader("Authorization", token);
//        getRedirectStrategy().sendRedirect(request, response, "http://localhost:5173/redirect-uri?token="+token);
        getRedirectStrategy().sendRedirect(request, response, "http://localhost:5173/redirect-uri?token="+token+"&refresh="+refresh);
        // 프론트 url
//        response.sendRedirect("http://localhost:5173/");
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setRefresh(refresh);
        refreshEntity.setUsername(username);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

//    private Cookie createCookie(String key, String value) {
//        Cookie cookie = new Cookie(key, value);
//        cookie.setMaxAge(60*10);     //  S
//        cookie.setPath("/");
//        cookie.setHttpOnly(false);
//
//        return cookie;
//
//    }
}

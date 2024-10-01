package com.kb.zipkim.domain.login.oauth2;

import com.kb.zipkim.domain.login.dto.CustomOAuth2User;
import com.kb.zipkim.domain.login.entity.RefreshEntity;
import com.kb.zipkim.domain.login.jwt.JWTUtil;
import com.kb.zipkim.domain.login.repository.RefreshRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    private final RefreshRepository refreshRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority auth = iterator.next();
//        String role = auth.getAuthority();

        String username = customUserDetails.getUsername();
        String email = customUserDetails.getEmail();
        String role = customUserDetails.getRole();
        String name = customUserDetails.getName();

        String access = jwtUtil.createJwt("access", username, name, role, email,60 * 30 * 1000L);     // Ms
        String refresh = jwtUtil.createJwt("refresh", username, name, role, email,60 * 120 * 1000L);     // Ms

        addRefreshEntity(username, refresh, 60 * 120 * 1000L);

//        response.setHeader("access", access);
        response.addCookie(createCookie("access", access));
        response.addCookie(createCookie("refresh", refresh));
        // 프론트 url
        response.sendRedirect("http://localhost:5173/");
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = RefreshEntity.builder()
                .username(username)
                .refresh(refresh)
                .expiration(date.toString())
                .build();

        refreshRepository.save(refreshEntity);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 30);     //  S
        // cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(false);

        return cookie;

    }
}

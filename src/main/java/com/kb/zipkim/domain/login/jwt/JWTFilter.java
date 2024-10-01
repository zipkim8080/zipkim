package com.kb.zipkim.domain.login.jwt;

import com.kb.zipkim.domain.login.dto.CustomOAuth2User;
import com.kb.zipkim.domain.login.dto.UserDTO;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        if (requestUri.matches("^\\/login(?:\\/.*)?$") || requestUri.matches("^\\/oauth2(?:\\/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie);
                if (cookie.getName().equals("access")) {
                    accessToken = cookie.getValue();
                    System.out.println("access: " + accessToken);
                }
            }
        }


        if (accessToken == null) {

            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            PrintWriter writer = response.getWriter();
            writer.println("토큰이 만료되었습니다.");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {
            PrintWriter writer = response.getWriter();
            writer.print("유효하지 않은 토큰");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // token에서 username과 role 획득
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);
        String email = jwtUtil.getEmail(accessToken);
        String name = jwtUtil.getName(accessToken);

        // userDTO를 생성하여 값 set
        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .role(role)
                .email(email)
                .name(name)
                .build();
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername(username);
//        userDTO.setName(name);
//        userDTO.setRole(role);
//        userDTO.setEmail(email);

        // UserDetails에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

        // 스프링 security 인증 token 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        // 토큰을 세션(security context holder)에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = securityContext.getAuthentication();


        // 다음 필터 요청
        filterChain.doFilter(request, response);
    }
}

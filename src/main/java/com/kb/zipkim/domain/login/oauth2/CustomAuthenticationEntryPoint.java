package com.kb.zipkim.domain.login.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.zipkim.global.exception.ErrorResponse;
import com.kb.zipkim.global.exception.ExceptionCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // Set the response status code to 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Write a custom message to the response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        log.error(ExceptionCode.WITHOUT_TOKEN.getMessage());
        writer.write(objectMapper.writeValueAsString(ErrorResponse.builder().code(ExceptionCode.WITHOUT_TOKEN.getCode()).message(ExceptionCode.WITHOUT_TOKEN.getMessage()).build()));
        writer.flush();
    }
}

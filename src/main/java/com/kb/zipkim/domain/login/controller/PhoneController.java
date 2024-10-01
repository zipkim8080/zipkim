package com.kb.zipkim.domain.login.controller;


import com.kb.zipkim.domain.login.dto.AccessToken;
import com.kb.zipkim.domain.login.dto.CustomOAuth2User;
import com.kb.zipkim.domain.login.dto.PhoneNumberRequest;
import com.kb.zipkim.domain.login.jwt.JWTUtil;
import com.kb.zipkim.domain.login.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class PhoneController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    private String getAccessToken(HttpServletRequest request) {
        String accessToken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("access")) {
                accessToken = cookie.getValue();
            }
        }
        return accessToken;
    }

    @GetMapping("/phone")
    public ResponseEntity getPhone(HttpServletRequest request) {

        String accessToken = getAccessToken(request);

        String username = jwtUtil.getUsername(accessToken);
        String phoneNumber = userService.getPhoneNumber(username);

        if (phoneNumber != null) {
            return ResponseEntity.ok(phoneNumber);
        } else {
            return null;
        }
    }

    @PostMapping("/phone")
    public ResponseEntity<String> addPhone(@RequestBody PhoneNumberRequest phoneNumberRequest, HttpServletRequest request) {
        String phoneNumber = phoneNumberRequest.getPhoneNumber();
        String accessToken = getAccessToken(request);
        String username = jwtUtil.getUsername(accessToken);
        userService.addPhoneNumber(username, phoneNumber);
        return ResponseEntity.ok("저장 성공");
    }
}
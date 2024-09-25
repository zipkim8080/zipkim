package com.kb.zipkim.domain.login.controller;


import com.kb.zipkim.domain.login.dto.AccessToken;
import com.kb.zipkim.domain.login.dto.CustomOAuth2User;
import com.kb.zipkim.domain.login.dto.PhoneNumberRequest;
import com.kb.zipkim.domain.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class PhoneController {

    @Autowired
    private UserService userService;

    @PostMapping("/addPhone")
    public ResponseEntity<String> addPhone(@RequestBody PhoneNumberRequest phoneNumberRequest) {
        String phoneNumber = phoneNumberRequest.getPhoneNumber();
        String username = phoneNumberRequest.getUsername();
        userService.addPhoneNumber(username, phoneNumber);
        return ResponseEntity.ok("저장 성공");
    }

    /*private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            if(authentication.getPrincipal() instanceof CustomOAuth2User) {
                CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
                return customOAuth2User.getUsername();
            }
            return authentication.getName();
        }
        return null;*/
    }


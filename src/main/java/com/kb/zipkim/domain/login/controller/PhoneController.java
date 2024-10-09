package com.kb.zipkim.domain.login.controller;


import com.kb.zipkim.domain.login.dto.AccessToken;
import com.kb.zipkim.domain.login.dto.CustomOAuth2User;
import com.kb.zipkim.domain.login.dto.PhoneNumberRequest;
import com.kb.zipkim.domain.login.jwt.JWTUtil;
import com.kb.zipkim.domain.login.service.UserService;
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

    @GetMapping("/phone")
    public ResponseEntity getPhone(HttpServletRequest request) {
        String username =jwtUtil.getUsername(request.getHeader("Authorization").substring(7));
        String phoneNumber = userService.getPhoneNumber(username);
        String brokerNumber = userService.getBrokerNumber(username);
        if (phoneNumber != null || brokerNumber != null) {
            PhoneNumberRequest response = new PhoneNumberRequest(phoneNumber, brokerNumber);
            return ResponseEntity.ok(response);
        } else {
            return null;
        }
    }

    @PostMapping("/phone")
    public ResponseEntity<String> addPhone(@RequestBody PhoneNumberRequest phoneNumberRequest, HttpServletRequest request) {
        String phoneNumber = phoneNumberRequest.getPhoneNumber();
        String brokerNumber = phoneNumberRequest.getBrokerNumber();
        String username =jwtUtil.getUsername(request.getHeader("Authorization").substring(7));
//        String username = phoneNumberRequest.getUsername();
        userService.addPhoneNumber(username, phoneNumber, brokerNumber);
        return ResponseEntity.ok("저장 성공");
    }
}
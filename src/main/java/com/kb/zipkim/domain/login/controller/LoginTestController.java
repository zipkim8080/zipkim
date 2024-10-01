package com.kb.zipkim.domain.login.controller;

import com.kb.zipkim.domain.login.dto.CustomOAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("/test")
public class LoginTestController {

    @GetMapping("/user-info1")
    public String check(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        if (customOAuth2User != null) {
            System.out.println("info1: " + customOAuth2User.getEmail());
            return customOAuth2User.getEmail();
        } else {
            return "뭔가 잘못 됐는데?";
        }
    }
}


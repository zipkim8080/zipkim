package com.kb.zipkim.domain.login.controller;

import com.kb.zipkim.domain.login.dto.AccessToken;
import com.kb.zipkim.domain.login.dto.CustomOAuth2User;
import com.kb.zipkim.domain.login.dto.UserDTO;
import com.kb.zipkim.domain.login.jwt.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/kakao")
public class LoginController {
    @Autowired
    private AccessToken tokenService;

    private final String KAKAO_USER_INFO_API = "https://kapi.kakao.com/v2/user/info";

    @GetMapping("/login")
    public ResponseEntity<String> kakaoLogin(HttpServletResponse response){
        String accessToken = tokenService.getAccessToken();
        return ResponseEntity.ok(accessToken);
    }

    /*@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> kakaoLogin(@RequestParam(required = false) String token,
                                             @RequestBody(required = false) Map<String, String> requestBody) {
        String accessToken = tokenService.getAccessToken();
        if(accessToken != null && !accessToken.isEmpty()) {
            return ResponseEntity.ok(accessToken);
        } else {
            return ResponseEntity.ok(accessToken);
        }

    }*/
}

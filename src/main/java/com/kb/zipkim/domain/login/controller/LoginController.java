package com.kb.zipkim.domain.login.controller;


import com.kb.zipkim.domain.login.dto.AccessToken;
import com.kb.zipkim.domain.login.dto.CustomOAuth2User;
import com.kb.zipkim.domain.login.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/kakao")
public class LoginController {

    @Autowired
    private AccessToken tokenService;

    private final String KAKAO_USER_INFO_API = "https://kapi.kakao.com/v2/user/info";

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> kakaoLogin(@RequestParam(required = false) String token,
                                             @RequestBody(required = false) Map<String, String> requestBody) {
        String accessToken = tokenService.getAccessToken();
        if(accessToken != null && !accessToken.isEmpty()) {
            return ResponseEntity.ok("로그인 완료");
        } else {
            return ResponseEntity.ok("로그인 필요");
        }

    }
    /*private ResponseEntity<String> processKakaoLogin(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(KAKAO_USER_INFO_API, org.springframework.http.HttpMethod.GET, entity, Map.class);
            Map<String, Object> userInfo = response.getBody();

            System.out.println("카카오 사용자 정보: " + userInfo);

            return new ResponseEntity<>("카카오 로그인 성공", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("카카오 로그인 실패");
            return new ResponseEntity<>("카카오 로그인 실패", HttpStatus.UNAUTHORIZED);
        }

    }*/
}

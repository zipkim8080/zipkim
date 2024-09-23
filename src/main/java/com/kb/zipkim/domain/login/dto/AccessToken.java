package com.kb.zipkim.domain.login.dto;

import org.springframework.stereotype.Service;

@Service
public class AccessToken {
    private String accessToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getAccessToken() {
        return accessToken;
    }
}

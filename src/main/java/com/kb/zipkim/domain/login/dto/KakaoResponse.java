package com.kb.zipkim.domain.login.dto;

import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attribute;
    private final Map<String, Object> kakaoAccount;

    // 생성자에서 attribute에서 "kakao_account"를 꺼내서 사용하도록 변경
    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
        this.kakaoAccount = (Map<String, Object>) attribute.get("kakao_account"); // "kakao_account"에서 필요한 정보를 가져옴
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString(); // "id"는 Long 타입이므로 Map이 아님, 그대로 사용
    }

    @Override
    public String getEmail() {
        return kakaoAccount.get("email").toString(); // "kakao_account"에서 email을 가져옴
    }

    @Override
    public String getName() {
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        return profile.get("nickname").toString(); // "profile"에서 nickname을 가져옴
    }
}

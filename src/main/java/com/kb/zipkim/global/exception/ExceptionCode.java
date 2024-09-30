package com.kb.zipkim.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {
    EXPIRED_PERIOD_TOKEN(9103, "기한이 만료된 Token입니다."),
    INVALID_AUTHORIZATION_CODE(9001, "유효하지 않은 인증 코드입니다."),
    WITHOUT_TOKEN(9002,"로그인 해주세요."),
    INVALID_TOKEN(9000, "유효하지 않은 토큰입니다."),
    INVALID_REFRESH_TOKEN(9004,"유효하지 않은 refresh token 입니다."),;
    private final int code;
    private final String message;
}


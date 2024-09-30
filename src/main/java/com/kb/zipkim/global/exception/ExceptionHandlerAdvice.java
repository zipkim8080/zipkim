package com.kb.zipkim.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler(AuthException.class)
    public ErrorResponse handleAuthException(AuthException e) {
        log.error("토큰이 없습니다.");
        return new ErrorResponse(e.getMessage(), e.getCode());
    }
}

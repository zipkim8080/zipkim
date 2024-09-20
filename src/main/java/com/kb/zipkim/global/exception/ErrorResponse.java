package com.kb.zipkim.global.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {
    private String message;
    private Integer code;

    public ErrorResponse(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}

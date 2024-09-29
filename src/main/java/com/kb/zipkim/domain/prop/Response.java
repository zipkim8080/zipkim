package com.kb.zipkim.domain.prop;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> {
    private String resultCode;
    private T data;
    public static <T> Response<T> success(T data) {
        return new Response<>("200", data);
    }
}

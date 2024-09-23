package com.kb.zipkim.domain.prop;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ComplexListResponse<T> {
    private String resultCode;
    private T data;
    public static <T> ComplexListResponse<T> success(T data) {
        return new ComplexListResponse<>("200", data);
    }
}

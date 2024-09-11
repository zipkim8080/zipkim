package com.kb.zipkim.domain.prop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResult {

    Long propId;
    List<String> imageUrl = new ArrayList<>();
}

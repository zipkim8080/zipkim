package com.kb.zipkim.domain.complex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    Long complexId;

    String name;

    String type;

    String addressName;

    double latitude;

    double longitude;

}

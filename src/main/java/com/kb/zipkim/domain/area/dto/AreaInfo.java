package com.kb.zipkim.domain.area.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AreaInfo {
    private Long id;

    private double supplyArea;

    private String pyeongName;
}

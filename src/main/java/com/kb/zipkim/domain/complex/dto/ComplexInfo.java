package com.kb.zipkim.domain.complex.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplexInfo {

    private Long id;

    private String bgdCd;

    private String complexName;

    private String roadName;

    private Double recentAmount;

    private Double recentDeposit;

    private String addressName;

    private String mainAddressNo;

    private String subAddressNo;

}

package com.kb.zipkim.domain.complex.dto;

import com.kb.zipkim.domain.area.dto.AreaInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplexInfo {

    private Long id;

    private String type;

    private String bgdCd;

    private String complexName;

    private String roadName;

    private Double recentAmount;

    private Double recentDeposit;

    private String addressName;

    private String mainAddressNo;

    private String subAddressNo;

    private List<AreaInfo> areas = new ArrayList<>();
}

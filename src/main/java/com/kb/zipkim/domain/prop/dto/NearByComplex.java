package com.kb.zipkim.domain.prop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static org.springframework.util.StringUtils.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NearByComplex {
    Long complexId;

    String type;

    Double currentAverageDeposit;

    Double currentAverageAmount;

    Double recentAmount;

    Double recentDeposit;

    Double curDepositRatio;
    Double recentDepositRatio;

    Double longitude;

    Double latitude;

    Double distance;

    public NearByComplex(Long complexId,String type, Double currentAverageDeposit, Double currentAverageAmount, Double recentAmount, Double recentDeposit, Double curDepositRatio, Double recentDepositRatio, Double longitude, Double latitude) {
        this.complexId = complexId;
        this.type = type;
        this.currentAverageDeposit = currentAverageDeposit;
        this.currentAverageAmount = currentAverageAmount;
        this.recentAmount = recentAmount;
        this.recentDeposit = recentDeposit;
        this.curDepositRatio = curDepositRatio;
        this.recentDepositRatio = recentDepositRatio;
        this.longitude = longitude;
        this.latitude = latitude;
    }

}

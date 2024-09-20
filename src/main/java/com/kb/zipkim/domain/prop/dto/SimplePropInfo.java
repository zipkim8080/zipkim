package com.kb.zipkim.domain.prop.dto;

import com.kb.zipkim.domain.prop.entity.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimplePropInfo {

    private Long id;

    private String detailAddress;

    private Double amount; //매매가

    private Double deposit; //전세가

    private Integer floor; //현재층

    private String description;

    private String imageUrl;

    public SimplePropInfo(Property property,String imageUrl) {
        this.id = property.getId();
        this.amount = property.getAmount();
        this.deposit = property.getDeposit();
        this.floor = property.getFloor();
        this.detailAddress = property.getDetailAddress();
        this.description = property.getDescription();
        this.imageUrl = imageUrl;
    }

}

package com.kb.zipkim.domain.prop.dto;

import jakarta.annotation.Nonnull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class PropRegisterForm {

    private Long brokerId;

    private String type;

    private String zipcode;

    private String roadName;

    private String bgdCd;

    private String addressName;

    private String mainAddressNo;

    private String subAddressNo;

    private Double longitude;
    private Double latitude;

    private Double amount; //매매가

    private Double deposit; //전세가

    private String roomNo;

    private String bathNo;

    private Boolean hasEv;

    private String porch; //현관타입

    List<MultipartFile> images = new ArrayList<>();

    private Integer floor; //현재층

    private Integer totalFloor;

    private String description;

    private Boolean parking; //주차가능여부

    private Double recentAmount;

    private Double recentDeposit;

    private String hugNumber;

    private Boolean hasSchool;
    private Boolean hasConvenience;

    private String complexName;

//  등기관련

    private String registerUniqueNum;

}

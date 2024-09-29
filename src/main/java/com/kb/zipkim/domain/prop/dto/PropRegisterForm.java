package com.kb.zipkim.domain.prop.dto;

import jakarta.annotation.Nonnull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class PropRegisterForm {

    private Long brokerId;

    private Long complexId;

    private String type;

    private String roadName;

    private String bgdCd;

    private String addressName;

    private String detailAddress;

    private String mainAddressNo;

    private String subAddressNo;

    private Double longitude;
    private Double latitude;

    private double amount; //매매가

    private double deposit; //전세가

    private String roomNo;

    private String bathNo;

    private Boolean hasEv;

    private String porch; //현관타입

    List<MultipartFile> images = new ArrayList<>();

    private Integer floor; //현재층

    private Integer totalFloor;

    private String description;

    private Boolean parking; //주차가능여부

    private String hugNumber;

    private Boolean hasSchool;
    private Boolean hasConvenience;

//  등기관련

    private String uniqueNumber;

    private String openDate;

    private String address;

    private Boolean attachment1;

    private Boolean attachment2;

    private Boolean trust;

    private Boolean auction;

    private double loan;

    private double leaseAmount;

}

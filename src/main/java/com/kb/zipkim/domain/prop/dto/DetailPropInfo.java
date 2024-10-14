package com.kb.zipkim.domain.prop.dto;

import com.kb.zipkim.domain.prop.entity.Property;
import com.kb.zipkim.domain.register.dto.RegisterResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailPropInfo {

    private Long id;

    private String type;

    private Long brokerId;

    private String brokerNo;

    private String phoneNumber;

    private String name;

    private String address;

    private String roadName;

    private String complexName;

    private String detailAddress;

    private RegisterResponse register;

    private double amount;

    private double deposit;

    private String roomNo;

    private String bathNo;

    private Boolean hasEv;

    private String porch; //현관타입

    List<PropImage> images = new ArrayList<>();

    private Integer floor; //현재층

    private Integer totalFloor;

    private String description;

    private Boolean parking; //주차가능여부

    private String hugNumber;

    private Boolean hasSchool;

    private Boolean hasConvenience;

    public static DetailPropInfo toDetailPropInfo(Property property) {
        return DetailPropInfo.builder()
                .id(property.getId())
                .type(property.getComplex().getType())
                .brokerId(property.getBroker().getId())
                .brokerNo(property.getBroker().getBrokerNo())
                .phoneNumber(property.getBroker().getPhonenumber())
                .name(property.getBroker().getName())
                .address(property.getComplex().getAddressName())
                .detailAddress(property.getDetailAddress())
                .complexName(property.getComplex().getComplexName())
                .roadName(property.getComplex().getRoadName())
                .register(RegisterResponse.toResponse(property.getRegistered()))
                .amount(property.getAmount())
                .deposit(property.getDeposit())
                .roomNo(property.getRoomNo())
                .floor(property.getFloor())
                .totalFloor(property.getTotalFloor())
                .bathNo(property.getBathNo())
                .hasEv(property.getHasEv())
                .porch(property.getPorch())
                .description(property.getDescription())
                .parking(property.getParking())
                .hugNumber(property.getHugNumber())
                .hasSchool(property.getHasSchool())
                .hasConvenience(property.getHasConvenience())
                .build();
    }



}

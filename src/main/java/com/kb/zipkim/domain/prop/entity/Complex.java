package com.kb.zipkim.domain.prop.entity;

import com.kb.zipkim.domain.prop.dto.PropRegisterForm;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Complex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zipcode;

    private String dong;

    private String roadName;

    private String bgdCd;

    private String addressName;

    private String mainAddressNo;

    private String subAddressNo;

    private String longitude;

    private String latitude;

    private String recentAmount;

    private String recentDeposit;

    public static Complex makeComplex(
            PropRegisterForm form
    ) {
        Complex complex = new Complex();
        complex.zipcode = form.getZipcode();
        complex.roadName = form.getRoadName();
        complex.bgdCd = form.getBgdCd();
        complex.addressName = form.getAddressName();
        complex.mainAddressNo = form.getMainAddressNo();
        complex.subAddressNo = form.getSubAddressNo();
        complex.longitude = form.getLongitude();
        complex.latitude = form.getLatitude();
        complex.recentAmount = form.getRecentAmount();
        complex.recentDeposit = form.getRecentDeposit();
        return complex;
    }
}

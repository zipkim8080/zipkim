package com.kb.zipkim.domain.prop.entity;

import com.kb.zipkim.domain.prop.dto.PropRegisterForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Complex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String complexName;

    private String type;

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

    private Double totalPropAmount;

    private Double totalPropDeposit;

    private Long propsCount;

    @OneToMany(mappedBy = "complex")
    private List<Property> properties = new ArrayList<>();

    public static Complex makeComplex(
            PropRegisterForm form
    ) {
        Complex complex = new Complex();
        complex.dong = form.getDong();
        complex.type = form.getType();
        complex.complexName = form.getComplexName();
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
        complex.totalPropAmount = 0.0;
        complex.totalPropDeposit = 0.0;
        complex.propsCount = 0L;
        return complex;
    }

    public void addPropCount() {
        this.propsCount++;
    }

    public void addTotalAmount(String amount) {
        this.totalPropAmount += Double.parseDouble(amount);
    }

    public void addTotalDeposit(String deposit) {
        this.totalPropDeposit += Double.parseDouble(deposit);
    }
}

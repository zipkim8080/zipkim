package com.kb.zipkim.domain.complex.entity;

import com.kb.zipkim.domain.prop.dto.PropRegisterForm;
import com.kb.zipkim.domain.prop.entity.Property;
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
@Builder
public class Complex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String complexName;

    private String type;

    private String roadName;

    private String bgdCd;

    private String addressName;

    private String mainAddressNo;

    private String subAddressNo;

    private Double longitude;

    private Double latitude;

    private Double recentAmount;

    private Double recentDeposit;

    private Double totalPropAmount;

    private Double totalPropDeposit;

    private Long propsCount;

    @OneToMany(mappedBy = "complex")    
    private List<Property> properties = new ArrayList<>();

    public static Complex makeDdOrYr(
            PropRegisterForm form
    ) {
        Complex complex = new Complex();
        complex.type = form.getType();
        complex.roadName = form.getRoadName();
        complex.bgdCd = form.getBgdCd();
        complex.addressName = form.getAddressName();
        complex.recentDeposit = 0.0;
        complex.recentAmount = 0.0;
        complex.mainAddressNo = form.getMainAddressNo();
        complex.subAddressNo = form.getSubAddressNo();
        complex.longitude = form.getLongitude();
        complex.latitude = form.getLatitude();
        complex.totalPropAmount = 0.0;
        complex.totalPropDeposit = 0.0;
        complex.propsCount = 0L;
        return complex;
    }

    public void addPropCount() {
        this.propsCount++;
    }

    public void addTotalAmount(Double amount) {
        this.totalPropAmount += amount;
    }

    public void addTotalDeposit(Double deposit) {
        this.totalPropDeposit += deposit;
    }

    public void updateRecentAmountAndDeposit(Double amount, Double deposit) {
        this.recentAmount = amount;
        this.recentDeposit = deposit;
    }

    public Double calcAvrAmount() {
        return totalPropAmount / propsCount;
    }

    public Double calcAvrDeposit() {
        return totalPropDeposit / propsCount;
    }
    public Double calcCurrentDepositRatio() {
        return calcAvrDeposit() / calcAvrAmount();
    }

    public Double calcRecentDepositRatio() {
        if (recentAmount == 0.0 || recentAmount ==  null || recentDeposit == null)  {
            return 0.0;
        }
        return recentDeposit / recentAmount;
    }
}

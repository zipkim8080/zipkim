package com.kb.zipkim.domain.register.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Registered {  //등기

    @Id
    private String uniqueNumber;

    private String openDate;

    private String address;

    private Boolean attachment1;

    private Boolean attachment2;

    private Boolean trust;

    private Boolean auction;

    private double loan;

    private double leaseAmount;

    public void update(String openDate,String address,Boolean attachment1,Boolean attachment2,Boolean trust,Boolean auction, double loan, double leaseAmount) {
        this.openDate = openDate;
        this.address = address;
        this.attachment1 = attachment1;
        this.attachment2 = attachment2;
        this.trust = trust;
        this.auction = auction;
        this.loan = loan;
        this.leaseAmount = leaseAmount;
    }

}

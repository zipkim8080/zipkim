package com.kb.zipkim.domain.prop.entity;

import com.kb.zipkim.domain.prop.dto.PropRegisterForm;
import com.kb.zipkim.domain.prop.file.UploadFile;
import com.kb.zipkim.domain.register.Registered;
import com.kb.zipkim.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Property extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long brokerId; //중개인

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "register_id")
    private Registered registered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complex_id")
    private Complex complex;

    private String amount; //매매가

    private String deposit; //전세가

    private String roomNo;

    private String bathNo;

    private Boolean hasEv;

    private String porch; //현관타입

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "property")
    private List<UploadFile> images = new ArrayList<>();

    private Integer floor; //현재층

    private Integer totalFloor;

    private String description;

    private Boolean parking; //주차가능여부

    private String hugNumber;

    private Boolean hasSchool;
    private Boolean hasConvenience;
    public static Property makeProperty(
           PropRegisterForm form
    ) {
        Property property = new Property();
        property.brokerId = form.getBrokerId();
        property.amount = form.getAmount();
        property.deposit = form.getDeposit();
        property.roomNo = form.getRoomNo();
        property.bathNo = form.getBathNo();
        property.hasEv = form.getHasEv();
        property.porch = form.getPorch();
        property.floor = form.getFloor();
        property.totalFloor = form.getTotalFloor();
        property.description = form.getDescription();
        property.parking = form.getParking();
        property.hugNumber = form.getHugNumber();
        property.hasSchool = form.getHasSchool();
        property.hasConvenience = form.getHasConvenience();
        return property;
    }
    public void register(Registered registered) {
        this.registered = registered;
    }

    public void upload(List<UploadFile> images) {
        for (UploadFile image : images) {
            image.setProperty(this);
        }
        this.images = images;
    }

    public void belongTo(Complex complex) {
        this.complex = complex;
    }
}

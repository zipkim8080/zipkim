package com.kb.zipkim.domain.prop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kb.zipkim.domain.complex.entity.Complex;
import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.prop.dto.PropRegisterForm;
import com.kb.zipkim.domain.prop.file.UploadFile;
import com.kb.zipkim.domain.register.entity.Registered;
import com.kb.zipkim.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Property extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broker_id")
    private UserEntity broker; //중개인

    private String detailAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register_id")
    private Registered registered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complex_id")
    @JsonIgnore
    private Complex complex;

    private Double amount; //매매가

    private Double deposit; //전세가

    private String roomNo;

    private String bathNo;

    private Boolean hasEv;

    private String porch; //현관타입

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "property")
    @BatchSize(size = 10)
    private List<UploadFile> images = new ArrayList<>();

    private Integer floor; //현재층

    private Integer totalFloor;

    private String description;

    private Boolean parking; //주차가능여부

    private String hugNumber;

    private Boolean hasSchool;
    private Boolean hasConvenience;
    public static Property makeProperty(
           PropRegisterForm form,UserEntity userEntity
    ) {
        Property property = new Property();
        property.broker = userEntity;
        property.amount = form.getAmount();
        property.detailAddress = form.getDetailAddress();
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
        complex.getProperties().add(this);
        complex.addPropCount();
        complex.addTotalAmount(amount);
        complex.addTotalDeposit(deposit);
    }
}

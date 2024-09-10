package com.kb.zipkim.domain.prop.entity;

import com.kb.zipkim.domain.prop.file.UploadFile;
import com.kb.zipkim.domain.register.register.Registered;
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
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long broker_id; //중개인

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "register_id")
    private Registered registered;

    private String zipcode;

    private String roadName;

    private String bgdCd;

    private String addressName;

    private String mainAddressNo;

    private String subAddressNo;

    private String longitude;
    private String latitude;

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

    private String recentAmount;

    private String recentDeposit;

    private String hugNumber;


    public void register(Registered registered) {
        this.registered = registered;
    }

}

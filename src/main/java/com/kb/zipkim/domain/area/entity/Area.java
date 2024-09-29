package com.kb.zipkim.domain.area.entity;

import com.kb.zipkim.domain.complex.entity.Complex;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complex_id")
    private Complex complex;

    private Long pyeongNo;

    private double supplyArea;

    private String pyeongName;

}

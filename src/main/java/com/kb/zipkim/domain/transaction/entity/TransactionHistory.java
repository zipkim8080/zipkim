package com.kb.zipkim.domain.transaction.entity;

import com.kb.zipkim.domain.area.entity.Area;
import com.kb.zipkim.domain.transaction.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    private String tradeYear;
    private String tradeMonth;

    private double dealPrice;
    private String formattedPrice;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
}

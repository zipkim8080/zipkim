package com.kb.zipkim.domain.transaction.dto;

import com.kb.zipkim.domain.transaction.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TransactionResponse {
    private String tradeYear;

    private String tradeMonth;

    private double dealPrice;

    private String formattedPrice;

    private String transactionType;
}

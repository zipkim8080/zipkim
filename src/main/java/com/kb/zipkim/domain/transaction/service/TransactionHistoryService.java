package com.kb.zipkim.domain.transaction.service;

import com.kb.zipkim.domain.transaction.TransactionType;
import com.kb.zipkim.domain.transaction.dto.TransactionResponse;
import com.kb.zipkim.domain.transaction.entity.TransactionHistory;
import com.kb.zipkim.domain.transaction.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;

    public Page<TransactionResponse> findHistory(Long areaId, String type, Pageable pageable) {

        Page<TransactionHistory> transactions = transactionHistoryRepository.findByAreaIdAndType(areaId, TransactionType.valueOf(type.toUpperCase()), pageable);
        List<TransactionResponse> responses = transactions.stream()
                .map(transaction -> TransactionResponse.builder()
                        .tradeYear(transaction.getTradeYear())
                        .tradeMonth(transaction.getTradeMonth())
                        .formattedPrice(transaction.getFormattedPrice())
                        .dealPrice(transaction.getDealPrice())
                        .transactionType(transaction.getType().name())
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, transactions.getTotalElements());
    }

}

package com.kb.zipkim.domain.transaction.controller;

import com.kb.zipkim.domain.transaction.dto.TransactionResponse;
import com.kb.zipkim.domain.transaction.service.TransactionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    @GetMapping("/api/price")
    public Page<TransactionResponse> getList(@RequestParam Long areaId,@RequestParam String type, Pageable pageable) {
        return transactionHistoryService.findHistory(areaId,type,pageable);
    }

}

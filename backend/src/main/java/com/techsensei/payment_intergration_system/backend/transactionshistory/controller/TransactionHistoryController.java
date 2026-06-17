package com.techsensei.payment_intergration_system.backend.transactionshistory.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsensei.payment_intergration_system.backend.transactionshistory.dto.TransactionHistoryResponse;
import com.techsensei.payment_intergration_system.backend.transactionshistory.service.servicelmpl.TransactionHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<TransactionHistoryResponse>> getTransactions(@PathVariable Long userId){

        return ResponseEntity.ok(transactionHistoryService.getTransactions(userId));
    }

}

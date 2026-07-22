package com.techsensei.payment_intergration_system.backend.transactionshistory.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsensei.payment_intergration_system.backend.transactionshistory.dto.TransactionHistoryResponse;
import com.techsensei.payment_intergration_system.backend.transactionshistory.service.servicelmpl.TransactionHistoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@Tag(name = "Transactions")
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    @Operation(summary = "Get transaction history")
    @GetMapping("/{userId}")
    public ResponseEntity<List<TransactionHistoryResponse>> getTransactions(@PathVariable Long userId){

        return ResponseEntity.ok(transactionHistoryService.getTransactions(userId));
    }

}

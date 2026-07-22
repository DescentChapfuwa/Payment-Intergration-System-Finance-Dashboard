package com.techsensei.payment_intergration_system.backend.withdrawals.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsensei.payment_intergration_system.backend.withdrawals.dto.WithdrawalRequest;
import com.techsensei.payment_intergration_system.backend.withdrawals.dto.WithdrawalResponse;
import com.techsensei.payment_intergration_system.backend.withdrawals.service.WithdrawalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@Tag(name = "Withdrawals")
@RestController
@RequestMapping("/api/withdrawals")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    
    @Operation(summary = "Initiate withdrawal")
    @PostMapping("/{userId}")
    public ResponseEntity<WithdrawalResponse> requestWithdrawal(@RequestHeader("Idempotency-Key") String key,@PathVariable Long userId,@RequestBody WithdrawalRequest request) {

        return ResponseEntity.ok(withdrawalService.requestWithdrawal(key,userId,request));
    }
    
    @Operation(summary = "Get withdrawal history")
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<WithdrawalResponse>> getHistory(@PathVariable Long userId){

        return ResponseEntity.ok(withdrawalService.requestAllWithdrawals(userId));
    }
}

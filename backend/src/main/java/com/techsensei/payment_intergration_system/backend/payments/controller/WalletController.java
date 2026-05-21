package com.techsensei.payment_intergration_system.backend.payments.controller;

import com.techsensei.payment_intergration_system.backend.payments.dto.WalletResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.WalletTopUpRequest;
import com.techsensei.payment_intergration_system.backend.payments.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/topup/{userId}")
    public ResponseEntity<WalletResponse> topUpWallet(@PathVariable Long userId, @Valid @RequestBody WalletTopUpRequest request) {

        return ResponseEntity.ok(
                walletService.topUpWallet(
                        userId,
                        request
                )
        );
    }
}

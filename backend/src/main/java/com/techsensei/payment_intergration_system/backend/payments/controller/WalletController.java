package com.techsensei.payment_intergration_system.backend.payments.controller;

import com.techsensei.payment_intergration_system.backend.common.exception.ResourceNotFoundException;
import com.techsensei.payment_intergration_system.backend.payments.dto.PagedResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.TransactionResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.WalletResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.WalletTopUpRequest;
import com.techsensei.payment_intergration_system.backend.payments.service.WalletService;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Wallet")
@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/topup/{userId}")
    public ResponseEntity<WalletResponse> topUpWallet(@PathVariable Long userId, @Valid @RequestBody WalletTopUpRequest request) {

        return ResponseEntity.ok(walletService.topUpWallet(userId, request));
    }

    @Operation(summary = "Get wallet balance")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/balance")
    public ResponseEntity<WalletResponse> getBalance(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return ResponseEntity.ok(walletService.getWalletBalance(user.getId()));
    }

    @GetMapping("/transactions")
    public ResponseEntity<PagedResponse<TransactionResponse>> getTransactions(Authentication authentication, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return ResponseEntity.ok(walletService.getTransactionHistory(user.getId(), page, size)
        );
    }
}

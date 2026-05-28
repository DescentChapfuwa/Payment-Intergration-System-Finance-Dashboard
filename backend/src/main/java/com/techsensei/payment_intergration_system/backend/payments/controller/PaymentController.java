package com.techsensei.payment_intergration_system.backend.payments.controller;

import com.techsensei.payment_intergration_system.backend.common.exception.ResourceNotFoundException;
import com.techsensei.payment_intergration_system.backend.payments.dto.PagedResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentHistoryResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentRequest;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentResponse;
import com.techsensei.payment_intergration_system.backend.payments.entity.PaymentStatus;
import com.techsensei.payment_intergration_system.backend.payments.service.PaymentService;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    private final UserRepository userRepository;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/send")
    public ResponseEntity<PaymentResponse> sendMoney(@Valid @RequestBody PaymentRequest request,Authentication authentication) {

        String email = authentication.getName();

        PaymentResponse response = paymentService.sendMoney(email, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/history")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<PagedResponse<PaymentHistoryResponse>> getPaymentHistory(Authentication authentication,
            @RequestParam(required = false) PaymentStatus status,
            @RequestParam(required = false) String reference,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return ResponseEntity
                .ok(paymentService.getPaymentHistory(user.getId(), status, reference, minAmount, page, size));
    }

}

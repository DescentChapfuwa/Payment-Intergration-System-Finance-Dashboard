package com.techsensei.payment_intergration_system.backend.payments.controller;

import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentRequest;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentResponse;
import com.techsensei.payment_intergration_system.backend.payments.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/send")
    public ResponseEntity<PaymentResponse> sendMoney(@Valid @RequestBody PaymentRequest request, Authentication authentication) {

        String email = authentication.getName();

        PaymentResponse response = paymentService.sendMoney(email, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

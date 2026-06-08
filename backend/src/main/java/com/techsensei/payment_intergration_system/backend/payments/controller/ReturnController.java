package com.techsensei.payment_intergration_system.backend.payments.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class ReturnController {

    @GetMapping("/return")
    public ResponseEntity<String> paymentReturn() {

        return ResponseEntity.ok(
                "Payment completed. You may close this page.");
    }
}

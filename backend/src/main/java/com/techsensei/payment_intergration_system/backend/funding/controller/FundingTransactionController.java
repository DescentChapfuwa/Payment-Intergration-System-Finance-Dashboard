package com.techsensei.payment_intergration_system.backend.funding.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsensei.payment_intergration_system.backend.funding.dto.FundingRequest;
import com.techsensei.payment_intergration_system.backend.funding.dto.FundingResponse;
import com.techsensei.payment_intergration_system.backend.funding.service.FundingService;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/funding")
@RequiredArgsConstructor
public class FundingTransactionController {

    private final FundingService fundingService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/initiate/{userld}")
    public ResponseEntity<FundingResponse> sendMoney(@Valid @RequestBody FundingRequest request,@PathVariable Long userld) {

        FundingResponse response = fundingService.initiateFunding(userld, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
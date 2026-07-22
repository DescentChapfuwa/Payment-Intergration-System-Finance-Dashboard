package com.techsensei.payment_intergration_system.backend.funding.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsensei.payment_intergration_system.backend.funding.dto.FundingRequest;
import com.techsensei.payment_intergration_system.backend.funding.dto.FundingResponse;
import com.techsensei.payment_intergration_system.backend.funding.dto.FundingTransactionDetails;
import com.techsensei.payment_intergration_system.backend.funding.repository.FundingTransactionRepository;
import com.techsensei.payment_intergration_system.backend.funding.service.FundingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;@Tag(
        name = "Funding"
)

@Tag(name = "Funding")
@RestController
@RequestMapping("/api/funding")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class FundingTransactionController {

    private final FundingService fundingService;
    private final FundingTransactionRepository fundingRepository;


    @Operation(summary = "Initiate wallet funding")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/initiate/{userld}")
    public ResponseEntity<FundingResponse> sendMoney(@Valid @RequestBody FundingRequest request,
            @PathVariable Long userld) {

        FundingResponse response = fundingService.initiateFunding(userld, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get funding history")
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<FundingTransactionDetails>> getFundingHistory(@PathVariable Long userId) {

        return ResponseEntity.ok(fundingService.getAllTransactions(userId));
    }

}
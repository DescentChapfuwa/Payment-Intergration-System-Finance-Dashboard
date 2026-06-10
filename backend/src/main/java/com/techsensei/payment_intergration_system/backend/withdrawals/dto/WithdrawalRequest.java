package com.techsensei.payment_intergration_system.backend.withdrawals.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawalRequest {

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    private String provider;
}
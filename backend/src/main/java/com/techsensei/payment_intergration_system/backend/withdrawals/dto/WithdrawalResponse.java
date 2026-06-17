package com.techsensei.payment_intergration_system.backend.withdrawals.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.techsensei.payment_intergration_system.backend.withdrawals.enums.WithdrawalStatus;

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
public class WithdrawalResponse {

    private UUID reference;

    private WithdrawalStatus status;

    private String message;

    private BigDecimal amount;
}

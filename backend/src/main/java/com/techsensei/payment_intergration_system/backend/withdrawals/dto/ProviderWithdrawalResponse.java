package com.techsensei.payment_intergration_system.backend.withdrawals.dto;

import com.techsensei.payment_intergration_system.backend.withdrawals.enums.WithdrawalStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderWithdrawalResponse {

    private WithdrawalStatus status;

    private String providerReference;

    private String message;
}

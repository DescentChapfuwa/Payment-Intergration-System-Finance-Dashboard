package com.techsensei.payment_intergration_system.backend.funding.dto;

import java.math.BigDecimal;

import com.techsensei.payment_intergration_system.backend.funding.enums.FundingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundingTransactionDetails {


    private String reference;

    private BigDecimal amount;

    private FundingStatus status;
}

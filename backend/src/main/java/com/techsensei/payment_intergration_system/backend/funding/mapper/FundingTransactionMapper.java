package com.techsensei.payment_intergration_system.backend.funding.mapper;

import com.techsensei.payment_intergration_system.backend.funding.dto.FundingTransactionDetails;
import com.techsensei.payment_intergration_system.backend.funding.entity.FundingTransaction;

public class FundingTransactionMapper {

    public static FundingTransactionDetails mapToResponse(FundingTransaction transaction) {

        return FundingTransactionDetails
                .builder()
                .amount(transaction.getAmount())
                .reference(transaction.getReference().toString())
                .status(transaction.getStatus())
                .build();
    }
}

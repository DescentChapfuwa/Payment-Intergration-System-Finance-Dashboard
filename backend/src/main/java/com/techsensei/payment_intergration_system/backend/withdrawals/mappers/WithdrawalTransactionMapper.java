package com.techsensei.payment_intergration_system.backend.withdrawals.mappers;

import com.techsensei.payment_intergration_system.backend.withdrawals.dto.WithdrawalResponse;
import com.techsensei.payment_intergration_system.backend.withdrawals.entity.WithdrawalTransaction;

public class WithdrawalTransactionMapper {

    public static WithdrawalResponse mapToResponse(WithdrawalTransaction transaction) {

        return WithdrawalResponse
                .builder()
                .amount(transaction.getAmount())
                .reference(transaction.getReference())
                .status(transaction.getStatus())
                .build();
    }
}

package com.techsensei.payment_intergration_system.backend.withdrawals.providers.ecocash;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.techsensei.payment_intergration_system.backend.withdrawals.dto.ProviderWithdrawalResponse;
import com.techsensei.payment_intergration_system.backend.withdrawals.enums.WithdrawalStatus;
import com.techsensei.payment_intergration_system.backend.withdrawals.providers.WithdrawalProvider;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EcocashWithdrawalProvider implements WithdrawalProvider {
    @Override
    public ProviderWithdrawalResponse processWithdrawal(UUID reference, BigDecimal amount) {
        log.info("Processing EcoCash withdrawal reference={}",reference);

        return ProviderWithdrawalResponse
                .builder()
                .status(WithdrawalStatus.SUCCESS)
                .providerReference(UUID.randomUUID().toString())
                .message("Withdrawal successful")
                .build();
    }

    @Override
    public String getProviderName() {
        return "ECOCASH";
    }

}
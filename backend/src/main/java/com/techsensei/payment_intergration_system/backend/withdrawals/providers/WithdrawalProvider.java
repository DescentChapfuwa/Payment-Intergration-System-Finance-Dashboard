package com.techsensei.payment_intergration_system.backend.withdrawals.providers;

import java.math.BigDecimal;
import java.util.UUID;


import com.techsensei.payment_intergration_system.backend.withdrawals.dto.ProviderWithdrawalResponse;

public interface WithdrawalProvider {

        ProviderWithdrawalResponse processWithdrawal(UUID reference,BigDecimal amount);

        String getProviderName();
}



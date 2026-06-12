package com.techsensei.payment_intergration_system.backend.withdrawals.providers;

import java.util.List;

import org.springframework.stereotype.Component;


import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WithdrawalProviderFactory {

    private final List<WithdrawalProvider> providers;

    public WithdrawalProvider getProvider(String providerName) {
        return providers
                .stream()
                .filter(provider -> provider
                        .getProviderName()
                        .equalsIgnoreCase(providerName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Provider not supported"));
    }
}

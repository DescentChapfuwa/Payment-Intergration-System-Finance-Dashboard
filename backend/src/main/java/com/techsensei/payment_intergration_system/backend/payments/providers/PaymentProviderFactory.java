package com.techsensei.payment_intergration_system.backend.payments.providers;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentProviderFactory {

    private final List<PaymentProvider> providers;

    public PaymentProvider getProvider(String providerName) {
        return providers
                .stream()
                .filter(provider -> provider
                        .getProviderName()
                        .equalsIgnoreCase(providerName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Provider not supported"));
    }
}

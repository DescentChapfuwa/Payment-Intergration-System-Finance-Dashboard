package com.techsensei.payment_intergration_system.backend.payments.providers;

import java.math.BigDecimal;

import com.techsensei.payment_intergration_system.backend.payments.dto.ProviderPaymentResponse;

public interface PaymentProvider {

    ProviderPaymentResponse processPayment(String customerEmail,BigDecimal amount);

    String getProviderName();
}

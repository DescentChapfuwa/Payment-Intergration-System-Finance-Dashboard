package com.techsensei.payment_intergration_system.backend.payments.providers.paynow;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.techsensei.payment_intergration_system.backend.payments.clients.PaynowHttpClient;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaynowApiResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.ProviderPaymentResponse;
import com.techsensei.payment_intergration_system.backend.payments.providers.PaymentProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaynowProvider implements PaymentProvider {

    private final PaynowHttpClient paynowHttpClient;

    @Override
    public ProviderPaymentResponse processPayment(String customerEmail, BigDecimal amount) {

        log.info("Processing Paynow payment");

        PaynowApiResponse response = paynowHttpClient.initiatePayment(customerEmail,amount);

        return ProviderPaymentResponse
                .builder()
                .success("SUCCESS".equals(response.getStatus()))
                .providerReference(response.getReference())
                .message(response.getMessage())
                .build();

    }

    @Override
    public String getProviderName() {

        return "PAYNOW";
    }
}

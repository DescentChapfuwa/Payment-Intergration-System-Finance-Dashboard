package com.techsensei.payment_intergration_system.backend.payments.providers.paynow;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.techsensei.payment_intergration_system.backend.payments.dto.ProviderPaymentResponse;
import com.techsensei.payment_intergration_system.backend.payments.providers.PaymentProvider;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PaynowProvider implements PaymentProvider {

    @Override
    public ProviderPaymentResponse
    processPayment(String customerEmail, BigDecimal amount){

        log.info("Processing Paynow payment");

        /*
           Simulated provider call
         */

        return ProviderPaymentResponse
                .builder()
                .success(true)
                .providerReference( "PAYNOW-" + UUID.randomUUID())
                .message("Payment processed")
                .build();
    }

    @Override
    public String getProviderName(){

        return "PAYNOW";
    }
}

package com.techsensei.payment_intergration_system.backend.payments.providers.paynow;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.techsensei.payment_intergration_system.backend.config.PaynowProperties;
import com.techsensei.payment_intergration_system.backend.payments.dto.ProviderPaymentResponse;
import com.techsensei.payment_intergration_system.backend.payments.entity.PaymentStatus;
import com.techsensei.payment_intergration_system.backend.payments.providers.PaymentProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zw.co.paynow.core.Paynow;
import zw.co.paynow.responses.WebInitResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaynowProvider implements PaymentProvider {

    private final Paynow paynow;

    private final PaynowProperties paynowProperties;


    @Override
    public String getProviderName() {
        return "PAYNOW";
    }

    @Override
    public ProviderPaymentResponse processPayment(String reference, String customerEmail, BigDecimal amount) {
        try {


            zw.co.paynow.core.Payment payment = paynow.createPayment(reference, paynowProperties.getMerchantEmail());

            payment.add("Wallet Funding", amount.doubleValue());

            WebInitResponse response = paynow.send(payment);

            if (response.success()) {

                return ProviderPaymentResponse
                        .builder()
                        .status(PaymentStatus.PENDING)
                        .providerReference(reference)
                        .checkoutUrl(response.redirectURL())
                        .pollUrl(response.pollUrl())
                        .message("Funding initiated")
                        .build();
            }

            return ProviderPaymentResponse
                    .builder()
                    .status(PaymentStatus.FAILED)
                    .message(response.errors())
                    .build();

        } catch (Exception ex) {

            log.error("Paynow payment initiation failed", ex);

            throw new RuntimeException("Unable to initiate payment");
        }
    }


    
}
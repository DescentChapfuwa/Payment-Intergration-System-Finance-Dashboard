package com.techsensei.payment_intergration_system.backend.payments.dto;

import com.techsensei.payment_intergration_system.backend.payments.entity.Payment;
import com.techsensei.payment_intergration_system.backend.payments.entity.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderPaymentResponse {

    private PaymentStatus status;

    private String providerReference;

    private String checkoutUrl;

    private String pollUrl;

    private String message;
}

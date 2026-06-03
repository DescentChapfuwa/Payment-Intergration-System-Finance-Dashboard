package com.techsensei.payment_intergration_system.backend.payments.dto;

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
public class WebhookPayload {

    private String providerReference;

    private String status;

    private String paymentReference;
}

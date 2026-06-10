package com.techsensei.payment_intergration_system.backend.payments.dto;

import java.math.BigDecimal;

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
public class PaynowWebhookPayload {

    private String reference;

    private String paynowReference;

    private BigDecimal amount;

    private String status;

    private String pollUrl;

    private String hash;
}

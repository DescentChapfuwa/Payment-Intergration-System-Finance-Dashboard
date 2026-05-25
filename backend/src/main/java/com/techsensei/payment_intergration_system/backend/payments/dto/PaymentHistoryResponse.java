package com.techsensei.payment_intergration_system.backend.payments.dto;

import com.techsensei.payment_intergration_system.backend.payments.entity.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentHistoryResponse {

    private String reference;

    private String senderEmail;

    private String receiverEmail;

    private BigDecimal amount;

    private PaymentStatus status;

    private LocalDateTime createdAt;
}

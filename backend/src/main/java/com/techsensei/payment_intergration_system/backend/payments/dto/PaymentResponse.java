package com.techsensei.payment_intergration_system.backend.payments.dto;


import com.techsensei.payment_intergration_system.backend.payments.entity.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long paymentId;

    private BigDecimal amount;

    private PaymentStatus status;

    private String reference;

    private String message;

}

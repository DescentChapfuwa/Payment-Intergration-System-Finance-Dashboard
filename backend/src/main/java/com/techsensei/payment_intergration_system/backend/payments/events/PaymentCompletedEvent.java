package com.techsensei.payment_intergration_system.backend.payments.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class PaymentCompletedEvent {

    private final Long paymentId;

    private final Long senderId;

    private final Long receiverId;

    private final BigDecimal amount;
}

package com.techsensei.payment_intergration_system.backend.withdrawals.events;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WithdrawalRequestedEvent {

    private Long userId;

    private UUID reference;

    private BigDecimal amount;
}

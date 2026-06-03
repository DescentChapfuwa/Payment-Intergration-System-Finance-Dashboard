package com.techsensei.payment_intergration_system.backend.funding.events;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FundingCompletedEvent {

    private Long userId;

    private UUID fundingReference;

    private BigDecimal amount;
}

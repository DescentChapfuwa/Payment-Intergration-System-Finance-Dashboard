package com.techsensei.payment_intergration_system.backend.transactionshistory.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryResponse {

    private String reference;

    private String type;

    private BigDecimal amount;

    private String status;

    private LocalDateTime createdAt;

}

package com.techsensei.payment_intergration_system.backend.payments.dto;

import com.techsensei.payment_intergration_system.backend.payments.entity.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {

    private Long transactionId;

    private BigDecimal amount;

    private TransactionType type;

    private LocalDateTime createdAt;

}

package com.techsensei.payment_intergration_system.backend.payments.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletResponse {

    private Long walletId;

    private BigDecimal balance;

    private String currency;
}

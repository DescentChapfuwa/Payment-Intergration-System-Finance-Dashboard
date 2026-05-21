package com.techsensei.payment_intergration_system.backend.payments.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletTopUpRequest {

    @NotNull
    @DecimalMin(value="0.01", message="Amount must be greater than zero")
    private BigDecimal amount;
}

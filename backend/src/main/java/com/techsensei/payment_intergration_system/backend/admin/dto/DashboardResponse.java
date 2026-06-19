package com.techsensei.payment_intergration_system.backend.admin.dto;

import java.math.BigDecimal;

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
public class DashboardResponse {

    private Long users;

    private BigDecimal totalWalletBalance;

    private Long fundings;

    private Long withdrawals;

}

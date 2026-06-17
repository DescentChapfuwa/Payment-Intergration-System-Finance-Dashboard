package com.techsensei.payment_intergration_system.backend.finance.service;

import java.math.BigDecimal;

public interface LedgerService {
    void recordFunding(String reference, BigDecimal amount);
    void recordWithdrawal(String reference, BigDecimal amount);
}

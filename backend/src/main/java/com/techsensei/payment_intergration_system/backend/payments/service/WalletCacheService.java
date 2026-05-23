package com.techsensei.payment_intergration_system.backend.payments.service;

import java.math.BigDecimal;

public interface WalletCacheService {

    void cacheBalance(Long userId, BigDecimal balance);

    BigDecimal getBalance(Long userId);

}

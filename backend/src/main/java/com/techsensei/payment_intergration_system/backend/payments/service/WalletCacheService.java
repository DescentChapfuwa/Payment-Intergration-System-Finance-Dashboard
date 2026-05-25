package com.techsensei.payment_intergration_system.backend.payments.service;

import com.techsensei.payment_intergration_system.backend.payments.dto.WalletResponse;

import java.math.BigDecimal;

public interface WalletCacheService {


    void cacheWallet(Long userId, WalletResponse walletResponse);

    WalletResponse getWallet(Long userId);

}

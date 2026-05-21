package com.techsensei.payment_intergration_system.backend.payments.service;

import com.techsensei.payment_intergration_system.backend.payments.dto.WalletResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.WalletTopUpRequest;
import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.users.entity.User;

public interface WalletService {

    Wallet createWallet(User user);

    WalletResponse topUpWallet(Long userId, WalletTopUpRequest request);
}

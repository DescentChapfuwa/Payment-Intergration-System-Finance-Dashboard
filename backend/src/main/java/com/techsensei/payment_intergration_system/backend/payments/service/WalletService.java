package com.techsensei.payment_intergration_system.backend.payments.service;

import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.users.entity.User;

public interface WalletService {

    Wallet createWallet(
            User user
    );
}

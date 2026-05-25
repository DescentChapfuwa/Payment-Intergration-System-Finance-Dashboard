package com.techsensei.payment_intergration_system.backend.payments.service;

import com.techsensei.payment_intergration_system.backend.payments.dto.PagedResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.WalletResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.WalletTopUpRequest;
import com.techsensei.payment_intergration_system.backend.payments.dto.TransactionResponse;
import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.users.entity.User;

import java.util.List;

public interface WalletService {

    Wallet createWallet(User user);

    WalletResponse getWalletBalance(Long userId);

    PagedResponse<TransactionResponse> getTransactionHistory(Long userId, int page, int size);

    WalletResponse topUpWallet(Long userId, WalletTopUpRequest request);
}

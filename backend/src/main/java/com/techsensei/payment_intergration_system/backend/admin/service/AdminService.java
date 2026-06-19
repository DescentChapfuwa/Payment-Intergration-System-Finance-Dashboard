package com.techsensei.payment_intergration_system.backend.admin.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.techsensei.payment_intergration_system.backend.admin.dto.DashboardResponse;
import com.techsensei.payment_intergration_system.backend.funding.repository.FundingTransactionRepository;
import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.payments.repository.WalletRepository;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;
import com.techsensei.payment_intergration_system.backend.withdrawals.repository.WithdrawalTransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    private final WalletRepository walletRepository;

    private final FundingTransactionRepository fundingRepository;

    private final WithdrawalTransactionRepository withdrawalRepository;

    public DashboardResponse dashboard() {

        BigDecimal balance = walletRepository
                .findAll()
                .stream()
                .map(Wallet::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return DashboardResponse.builder()
                .users(userRepository.count())
                .totalWalletBalance(balance)
                .fundings(fundingRepository.count())
                .withdrawals(withdrawalRepository.count())
                .build();
    }

}

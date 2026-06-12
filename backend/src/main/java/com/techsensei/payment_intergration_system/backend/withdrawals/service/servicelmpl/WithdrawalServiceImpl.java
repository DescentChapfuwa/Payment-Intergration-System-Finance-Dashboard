package com.techsensei.payment_intergration_system.backend.withdrawals.service.servicelmpl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.payments.repository.WalletRepository;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;
import com.techsensei.payment_intergration_system.backend.withdrawals.dto.WithdrawalRequest;
import com.techsensei.payment_intergration_system.backend.withdrawals.dto.WithdrawalResponse;
import com.techsensei.payment_intergration_system.backend.withdrawals.entity.WithdrawalTransaction;
import com.techsensei.payment_intergration_system.backend.withdrawals.enums.WithdrawalStatus;
import com.techsensei.payment_intergration_system.backend.withdrawals.events.WithdrawalRequestedEvent;
import com.techsensei.payment_intergration_system.backend.withdrawals.providers.WithdrawalProvider;
import com.techsensei.payment_intergration_system.backend.withdrawals.providers.WithdrawalProviderFactory;
import com.techsensei.payment_intergration_system.backend.withdrawals.repository.WithdrawalTransactionRepository;
import com.techsensei.payment_intergration_system.backend.withdrawals.service.WithdrawalService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WithdrawalServiceImpl implements WithdrawalService {

        private final UserRepository userRepository;

        private final WalletRepository walletRepository;

        private final WithdrawalTransactionRepository withdrawalTransactionRepository;

        private final ApplicationEventPublisher applicationEventPublisher;

        private final WithdrawalProviderFactory withdrawalProviderFactory;

        @Override
        public WithdrawalResponse requestWithdrawal(Long userId, WithdrawalRequest request) {

                User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

                Wallet wallet = walletRepository.findByUserIdForUpdate(userId)
                                .orElseThrow(() -> new RuntimeException("Wallet not found"));

                if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
                        throw new RuntimeException("Insufficient balance");
                }

                WithdrawalTransaction transaction = WithdrawalTransaction.builder()
                                .user(user)
                                .amount(request.getAmount())
                                .provider(request.getProvider())
                                .status(WithdrawalStatus.PENDING)
                                .build();

                withdrawalTransactionRepository.save(transaction);

                applicationEventPublisher.publishEvent( new WithdrawalRequestedEvent(
                                                user.getId(),
                                                transaction.getReference(),
                                                transaction.getAmount()));

                log.info("Withdrawal request created reference={} amount={}", transaction.getReference(),
                                transaction.getAmount());

                return WithdrawalResponse.builder()
                                .reference(transaction.getReference())
                                .message("Withdrawal request submitted successfully")
                                .status(transaction.getStatus())
                                .build();

        }

}
package com.techsensei.payment_intergration_system.backend.payments.service.servicelmpl;

import com.techsensei.payment_intergration_system.backend.payments.dto.WalletResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.WalletTopUpRequest;
import com.techsensei.payment_intergration_system.backend.payments.entity.Transaction;
import com.techsensei.payment_intergration_system.backend.payments.entity.TransactionType;
import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.payments.repository.TransactionRepository;
import com.techsensei.payment_intergration_system.backend.payments.repository.WalletRepository;
import com.techsensei.payment_intergration_system.backend.payments.service.WalletService;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Wallet createWallet(User user) {

        log.info(
                "Creating wallet for userId={}",
                user.getId()
        );

        Wallet wallet =
                Wallet.builder()
                        .user(user)
                        .balance(
                                BigDecimal.ZERO
                        )
                        .currency("USD")
                        .build();

        Wallet savedWallet =
                walletRepository.save(wallet);

        log.info(
                "Wallet created successfully. walletId={}",
                savedWallet.getId()
        );

        return savedWallet;
    }

    @Override
    @Transactional
    public WalletResponse topUpWallet(Long userId, WalletTopUpRequest request) {

        log.info(
                "Funding wallet userId={}, amount={}", userId, request.getAmount()
        );

        Wallet wallet = walletRepository
                        .findByUserIdForUpdate(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Wallet not found"
                                )
                        );



        wallet.setBalance(
                wallet.getBalance()
                        .add(request.getAmount())
        );

        walletRepository.save(wallet);

        Transaction transaction = Transaction.builder()
                        .wallet(wallet)
                        .amount(request.getAmount())
                        .type(TransactionType.CREDIT)
                        .createdAt(LocalDateTime.now())
                        .build();

        transactionRepository.save(transaction);

        log.info("Wallet funded successfully walletId={}", wallet.getId());

        return WalletResponse.builder()
                .walletId(wallet.getId())
                .balance(wallet.getBalance())
                .currency(wallet.getCurrency())
                .build();
    }
}

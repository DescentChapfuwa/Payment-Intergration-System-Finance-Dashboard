package com.techsensei.payment_intergration_system.backend.payments.service.servicelmpl;

import com.techsensei.payment_intergration_system.backend.payments.dto.PagedResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.TransactionResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.WalletResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.WalletTopUpRequest;
import com.techsensei.payment_intergration_system.backend.payments.entity.Transaction;
import com.techsensei.payment_intergration_system.backend.payments.entity.TransactionType;
import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.payments.repository.TransactionRepository;
import com.techsensei.payment_intergration_system.backend.payments.repository.WalletRepository;
import com.techsensei.payment_intergration_system.backend.payments.service.WalletCacheService;
import com.techsensei.payment_intergration_system.backend.payments.service.WalletService;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final WalletCacheService walletCacheService;
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
    public WalletResponse getWalletBalance(Long userId) {
        log.info("Fetching wallet balance for userId={}", userId);

        WalletResponse cachedWallet = walletCacheService.getWallet(userId);

        if(cachedWallet != null){

            log.info("Wallet loaded from cache");

            return cachedWallet;
        }

        log.info("Cache miss");

        Wallet wallet = walletRepository
                        .findByUserIdForUpdate(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

        WalletResponse response = WalletResponse.builder()
                        .walletId(wallet.getId())
                        .balance(wallet.getBalance())
                        .currency(wallet.getCurrency())
                        .build();

        walletCacheService.cacheWallet(userId, response);

        return response;
    }

    @Override
    @Transactional
    public PagedResponse<TransactionResponse> getTransactionHistory(Long userId, int page, int size){

        Wallet wallet = walletRepository.findByUserIdForUpdate(userId).orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Transaction> transactionPage = transactionRepository.findByWalletId(wallet.getId(), pageable);

        List<TransactionResponse> content = transactionPage
                        .getContent()
                        .stream()
                        .map(transaction -> TransactionResponse
                                .builder()
                                .transactionId(transaction.getId())
                                .amount(transaction.getAmount())
                                .type(transaction.getType())
                                .createdAt(transaction.getCreatedAt())
                                .build()
                        )
                        .toList();

        return PagedResponse
                .<TransactionResponse>builder()
                .content(content)
                .page(transactionPage.getNumber())
                .size(transactionPage.getSize())
                .totalElements(transactionPage.getTotalElements())
                .totalPages(transactionPage.getTotalPages())
                .last(transactionPage.isLast())
                .build();
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

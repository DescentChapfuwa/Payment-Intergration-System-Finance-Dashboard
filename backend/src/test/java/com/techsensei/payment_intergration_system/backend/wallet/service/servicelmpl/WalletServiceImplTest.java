package com.techsensei.payment_intergration_system.backend.wallet.service.servicelmpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.techsensei.payment_intergration_system.backend.payments.dto.WalletResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.WalletTopUpRequest;
import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.payments.repository.TransactionRepository;
import com.techsensei.payment_intergration_system.backend.payments.repository.WalletRepository;
import com.techsensei.payment_intergration_system.backend.payments.service.servicelmpl.WalletServiceImpl;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    @Test
    void shouldTopUpWallet() {

        Wallet wallet = Wallet.builder()
                .id(1L)
                .balance(BigDecimal.valueOf(100))
                .build();

        WalletTopUpRequest request = WalletTopUpRequest.builder()
        .amount(BigDecimal.valueOf(10))
        .build();

        when(walletRepository
            .findByUserIdForUpdate(1L))
            .thenReturn(Optional.of(wallet));

        WalletResponse result = walletService
        .topUpWallet(1L, request);

        assertNotEquals(BigDecimal.valueOf(100),result.getBalance());

        verify(walletRepository).save(wallet);
    }
}

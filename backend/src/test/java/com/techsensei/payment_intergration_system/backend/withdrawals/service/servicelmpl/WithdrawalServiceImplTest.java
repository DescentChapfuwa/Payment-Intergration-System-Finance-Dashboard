package com.techsensei.payment_intergration_system.backend.withdrawals.service.servicelmpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.payments.repository.WalletRepository;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;
import com.techsensei.payment_intergration_system.backend.withdrawals.dto.WithdrawalRequest;
import com.techsensei.payment_intergration_system.backend.withdrawals.entity.WithdrawalTransaction;
import com.techsensei.payment_intergration_system.backend.withdrawals.enums.WithdrawalStatus;
import com.techsensei.payment_intergration_system.backend.withdrawals.repository.IdempotencyKeyRepository;
import com.techsensei.payment_intergration_system.backend.withdrawals.repository.WithdrawalTransactionRepository;

@ExtendWith(MockitoExtension.class)
class WithdrawalServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WithdrawalTransactionRepository withdrawalRepository;

    @Mock
    private IdempotencyKeyRepository idempotencyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApplicationEventPublisher publisher;

    @InjectMocks
    private WithdrawalServiceImpl withdrawalService;

    @Test
    void shouldWithdrawSuccessfully() {

        User user = User.builder()
                .id(1L)
                .build();

        Wallet wallet = Wallet.builder()
                .balance(BigDecimal.valueOf(100))
                .build();

        WithdrawalTransaction savedTransaction = WithdrawalTransaction.builder()
                .reference(UUID.randomUUID())
                .amount(BigDecimal.valueOf(20))
                .status(WithdrawalStatus.PENDING)
                .user(user)
                .build();

        WithdrawalRequest request = WithdrawalRequest.builder()
                .amount(BigDecimal.valueOf(20))
                .build();

        when(userRepository.findById(1L)).thenReturn(
                Optional.of(user));

        when(walletRepository.findByUserIdForUpdate(1L)).thenReturn(
                Optional.of(wallet));

        when(withdrawalRepository.save(any(WithdrawalTransaction.class)))
                .thenReturn(savedTransaction);

        withdrawalService.requestWithdrawal("abc123", 1L, request);

        assertEquals(BigDecimal.valueOf(80), wallet.getBalance());

        verify(walletRepository).save(wallet);

        verify(withdrawalRepository).save(any());
    }

    @Test
    void shouldThrowInsufficientBalanceException() {

        WithdrawalRequest request = WithdrawalRequest.builder()
                .amount(BigDecimal.valueOf(20))
                .build();

        assertThrows(
                RuntimeException.class,
                () -> withdrawalService.requestWithdrawal(
                        "abc123",
                        1L,
                        request));
    }

    @Test
    void shouldRejectDuplicateRequest() {

         WithdrawalRequest request = WithdrawalRequest.builder()
                .amount(BigDecimal.valueOf(20))
                .build();

        assertThrows(
                RuntimeException.class,
                () -> withdrawalService.requestWithdrawal(
                        "abc123",
                        1L,
                        request));
    }
}

package com.techsensei.payment_intergration_system.backend.payments.service.servicelmpl;

import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.payments.repository.WalletRepository;
import com.techsensei.payment_intergration_system.backend.payments.service.WalletService;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

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
}

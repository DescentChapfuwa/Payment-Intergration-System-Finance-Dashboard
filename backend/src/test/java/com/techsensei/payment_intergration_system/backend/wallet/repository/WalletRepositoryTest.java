package com.techsensei.payment_intergration_system.backend.wallet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.payments.repository.WalletRepository;
import com.techsensei.payment_intergration_system.backend.users.entity.Role;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;

@DataJpaTest
public class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindWalletByUserId() {

        User user = User.builder()
                .fullName("John Doe")
                .email("john@test.com")
                .password("password")
                .role(Role.USER)
                .build();

        user = userRepository.save(user);

        Wallet wallet = Wallet.builder()
                .balance(BigDecimal.valueOf(100))
                .reservedBalance(BigDecimal.ZERO)
                .currency("USD")
                .user(user)
                .build();

        walletRepository.save(wallet);

        Optional<Wallet> found = walletRepository.findByUserIdForUpdate(user.getId());

        assertTrue(found.isPresent());
        assertEquals(BigDecimal.valueOf(100), found.get().getBalance());
    }

}

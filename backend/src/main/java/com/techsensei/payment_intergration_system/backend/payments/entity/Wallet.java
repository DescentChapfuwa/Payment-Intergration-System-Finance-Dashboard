package com.techsensei.payment_intergration_system.backend.payments.entity;

import com.techsensei.payment_intergration_system.backend.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Column(nullable = false)
    private String currency;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(nullable = false)
    @Builder.Default
    private BigDecimal reservedBalance = BigDecimal.ZERO;

    public void reserveFunds(BigDecimal amount) {

        if (balance.compareTo(amount) < 0) {
            throw new RuntimeException(
                    "Insufficient balance");
        }

        balance = balance.subtract(amount);

        reservedBalance = reservedBalance.add(amount);
    }

    public void completeWithdrawal(BigDecimal amount) {

        reservedBalance = reservedBalance.subtract(amount);
    }

    public void releaseFunds(BigDecimal amount) {

        reservedBalance = reservedBalance.subtract(amount);

        balance = balance.add(amount);
    }

}

package com.techsensei.payment_intergration_system.backend.payments.entity;

import com.techsensei.payment_intergration_system.backend.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id",nullable = false)
    private User sender;

    @Column(unique = true, nullable = false)
    private String reference;

    @ManyToOne
    @JoinColumn(name = "receiver_id",nullable = false)
    private User receiver;

    @Column(nullable = false,precision = 19,scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime createdAt;

}

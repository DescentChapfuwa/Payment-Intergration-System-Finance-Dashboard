package com.techsensei.payment_intergration_system.backend.withdrawals.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "idempotency_keys")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdempotencyKey {

    @Id
    @Column(name = "idempotency_key")
    private String idempotencyKey;

    private String endpoint;

    private String responseReference;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
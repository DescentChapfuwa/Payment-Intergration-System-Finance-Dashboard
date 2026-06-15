package com.techsensei.payment_intergration_system.backend.withdrawals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techsensei.payment_intergration_system.backend.withdrawals.entity.IdempotencyKey;



@Repository
public interface IdempotencyKeyRepository extends JpaRepository<IdempotencyKey,String> {
}
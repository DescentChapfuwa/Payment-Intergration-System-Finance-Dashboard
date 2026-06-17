package com.techsensei.payment_intergration_system.backend.withdrawals.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techsensei.payment_intergration_system.backend.withdrawals.entity.WithdrawalTransaction;

@Repository
public interface WithdrawalTransactionRepository extends JpaRepository<WithdrawalTransaction,Long>{

    Optional<WithdrawalTransaction> findByReference(UUID reference);

    List<WithdrawalTransaction> findByUserIdOrderByStatus(Long userId);
}

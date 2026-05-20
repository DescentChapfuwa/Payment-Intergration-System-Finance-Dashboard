package com.techsensei.payment_intergration_system.backend.payments.repository;

import com.techsensei.payment_intergration_system.backend.payments.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByWalletId(Long walletId);
}

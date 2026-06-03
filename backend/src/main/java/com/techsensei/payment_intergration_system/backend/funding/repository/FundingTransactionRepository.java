package com.techsensei.payment_intergration_system.backend.funding.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techsensei.payment_intergration_system.backend.funding.entity.FundingTransaction;

public interface FundingTransactionRepository extends JpaRepository<FundingTransaction,Long> {

    Optional<FundingTransaction> findByReference(UUID reference);

    Optional<FundingTransaction> findByProviderReference(String providerReference);
}

package com.techsensei.payment_intergration_system.backend.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techsensei.payment_intergration_system.backend.finance.entity.LedgerEntry;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry,Long> {
    
}

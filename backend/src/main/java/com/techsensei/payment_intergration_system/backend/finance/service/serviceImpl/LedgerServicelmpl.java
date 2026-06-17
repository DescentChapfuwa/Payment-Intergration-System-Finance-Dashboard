package com.techsensei.payment_intergration_system.backend.finance.service.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.techsensei.payment_intergration_system.backend.finance.entity.LedgerEntry;
import com.techsensei.payment_intergration_system.backend.finance.repository.LedgerEntryRepository;
import com.techsensei.payment_intergration_system.backend.finance.service.LedgerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LedgerServicelmpl implements LedgerService {

    private final LedgerEntryRepository repository;

    @Override
    public void recordFunding(String reference,BigDecimal amount){

        LedgerEntry entry = LedgerEntry.builder()
                        .reference(reference)
                        .type("FUNDING")
                        .amount(amount)
                        .description( "Wallet funding")
                        .createdAt(LocalDateTime.now())
                        .build();
        repository.save(entry);
    }


    public void recordWithdrawal(String reference,BigDecimal amount){

        LedgerEntry entry = LedgerEntry.builder()
                        .reference(reference)
                        .type("WITHDRAWAL")
                        .amount(amount)
                        .description( "Wallet withdrawal")
                        .createdAt(LocalDateTime.now())
                        .build();

        repository.save(entry);

    }

}
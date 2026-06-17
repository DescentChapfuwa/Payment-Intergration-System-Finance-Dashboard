package com.techsensei.payment_intergration_system.backend.finance.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsensei.payment_intergration_system.backend.finance.entity.LedgerEntry;
import com.techsensei.payment_intergration_system.backend.finance.repository.LedgerEntryRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerEntryRepository repository;

    @GetMapping("/ledger")
    public List<LedgerEntry> getLedger(){

        return repository.findAll();

    }

}

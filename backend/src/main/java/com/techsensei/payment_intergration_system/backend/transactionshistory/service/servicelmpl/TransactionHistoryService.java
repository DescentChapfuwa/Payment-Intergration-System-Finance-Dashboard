package com.techsensei.payment_intergration_system.backend.transactionshistory.service.servicelmpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.techsensei.payment_intergration_system.backend.funding.repository.FundingTransactionRepository;
import com.techsensei.payment_intergration_system.backend.transactionshistory.dto.TransactionHistoryResponse;
import com.techsensei.payment_intergration_system.backend.withdrawals.repository.WithdrawalTransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {

    private final FundingTransactionRepository fundingRepository;

    private final WithdrawalTransactionRepository withdrawalRepository;

    public List<TransactionHistoryResponse> getTransactions(Long userId){

        List<TransactionHistoryResponse> transactions = new ArrayList<>();


        fundingRepository.findByUserIdOrderByStatus(userId)
                .forEach(tx -> transactions.add(TransactionHistoryResponse.builder()
                                        .reference(tx.getReference().toString())
                                        .type("FUNDING")
                                        .amount(tx.getAmount())
                                        .status(tx.getStatus().name())
                                        .createdAt(tx.getCreatedAt())
                                        .build()
                        )

                );


        withdrawalRepository.findByUserIdOrderByStatus(userId)
                .forEach(tx ->transactions.add( TransactionHistoryResponse.builder()
                                        .reference(tx.getReference().toString())
                                        .type("WITHDRAWAL")
                                        .amount(tx.getAmount())
                                        .status(tx.getStatus().name())
                                        .createdAt(tx.getCreatedAt())
                                        .build()
                        )

                );


        return transactions.stream().sorted(Comparator.comparing(TransactionHistoryResponse::getCreatedAt).reversed()).toList();
    }

}

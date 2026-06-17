package com.techsensei.payment_intergration_system.backend.transactionshistory.service;

import java.util.List;

import com.techsensei.payment_intergration_system.backend.transactionshistory.dto.TransactionHistoryResponse;

public interface TransactionsHistoryService {

    List<TransactionHistoryResponse> getTransactionHistory(Long userId);  
    
}

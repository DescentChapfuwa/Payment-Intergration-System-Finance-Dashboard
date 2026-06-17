package com.techsensei.payment_intergration_system.backend.funding.service;

import java.util.List;

import com.techsensei.payment_intergration_system.backend.funding.dto.FundingRequest;
import com.techsensei.payment_intergration_system.backend.funding.dto.FundingResponse;
import com.techsensei.payment_intergration_system.backend.funding.dto.FundingTransactionDetails;

public interface FundingService {

    FundingResponse initiateFunding(Long userId,FundingRequest request);

    List<FundingTransactionDetails> getAllTransactions(Long userld);

}

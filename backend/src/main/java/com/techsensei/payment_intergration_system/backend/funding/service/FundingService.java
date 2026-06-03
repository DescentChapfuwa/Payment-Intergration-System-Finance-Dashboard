package com.techsensei.payment_intergration_system.backend.funding.service;

import com.techsensei.payment_intergration_system.backend.funding.dto.FundingRequest;
import com.techsensei.payment_intergration_system.backend.funding.dto.FundingResponse;

public interface FundingService {

    FundingResponse initiateFunding(Long userId,FundingRequest request);

}

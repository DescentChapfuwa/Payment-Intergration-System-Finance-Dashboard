package com.techsensei.payment_intergration_system.backend.funding.dto;

import java.util.UUID;

import com.techsensei.payment_intergration_system.backend.funding.enums.FundingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundingResponse {

    private UUID reference;

    private FundingStatus status;

    private String providerReference;

    private String checkoutUrl;

    private String pollUrl;

    private String message;
}

package com.techsensei.payment_intergration_system.backend.finance.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.techsensei.payment_intergration_system.backend.finance.service.LedgerService;
import com.techsensei.payment_intergration_system.backend.funding.events.FundingCompletedEvent;
import com.techsensei.payment_intergration_system.backend.withdrawals.events.WithdrawalRequestedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LedgerListener {

    private final LedgerService ledgerService;

    @EventListener
    public void handleFundingCompleted(FundingCompletedEvent event){

        ledgerService.recordFunding(event.getFundingReference().toString(),event.getAmount());

    }


    @EventListener
    public void handleWithdrawalCompleted(WithdrawalRequestedEvent event){

        ledgerService.recordWithdrawal(event.getReference().toString(), event.getAmount());

    }

}

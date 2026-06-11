package com.techsensei.payment_intergration_system.backend.withdrawals.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.techsensei.payment_intergration_system.backend.withdrawals.events.WithdrawalRequestedEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WithdrawalAuditListener {

    @EventListener
    public void handleWithdrawalRequested(WithdrawalRequestedEvent event){

        log.info(
            "Creating withdrawal audit entry reference={}",
            event.getReference()
        );
    }
}

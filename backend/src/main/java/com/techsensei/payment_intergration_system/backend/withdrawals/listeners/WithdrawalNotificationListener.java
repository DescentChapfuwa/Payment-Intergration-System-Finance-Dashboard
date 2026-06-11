package com.techsensei.payment_intergration_system.backend.withdrawals.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.techsensei.payment_intergration_system.backend.withdrawals.events.WithdrawalRequestedEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WithdrawalNotificationListener {
    @EventListener
    public void handleWithdrawalRequested(WithdrawalRequestedEvent event) {

        log.info(
                "Sending withdrawal notification reference={}",
                event.getReference());
    }
}

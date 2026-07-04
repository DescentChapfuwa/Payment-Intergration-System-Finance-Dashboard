package com.techsensei.payment_intergration_system.backend.withdrawals.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.techsensei.payment_intergration_system.backend.audit.service.AuditService;
import com.techsensei.payment_intergration_system.backend.withdrawals.events.WithdrawalRequestedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawalAuditListener {

    private final AuditService auditService;

    @EventListener
    public void handle(WithdrawalRequestedEvent event) {

        log.info("Creating withdrawal audit log for reference={}", event.getReference());

        auditService.log(
                event.getUserId(),
                "WITHDRAWAL_REQUESTED",
                "WITHDRAWAL",
                event.getReference().toString(),
                "Withdrawal requested for " + event.getAmount());

    }

}

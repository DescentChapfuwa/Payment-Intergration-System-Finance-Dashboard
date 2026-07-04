package com.techsensei.payment_intergration_system.backend.funding.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.techsensei.payment_intergration_system.backend.audit.service.AuditService;
import com.techsensei.payment_intergration_system.backend.funding.events.FundingCompletedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class FundingAuditListener {

    private final AuditService auditService;

    @EventListener
    public void handle(FundingCompletedEvent event) {

        log.info("Creating Audit Log for={}",event.getFundingReference());

        auditService.log(
                event.getUserId(),
                "FUNDING_COMPLETED",
                "FUNDING",
                event.getFundingReference().toString(),
                "Wallet funded with amount " + event.getAmount()
        );

    }

}

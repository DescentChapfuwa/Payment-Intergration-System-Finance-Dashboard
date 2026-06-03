package com.techsensei.payment_intergration_system.backend.funding.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.techsensei.payment_intergration_system.backend.funding.events.FundingCompletedEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FundingAuditListener {

    @EventListener
    public void handleAudit(FundingCompletedEvent event){

        log.info("Creating audit entry fundingReference={}", event.getFundingReference());

    }
}

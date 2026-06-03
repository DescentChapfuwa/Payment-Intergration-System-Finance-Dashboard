package com.techsensei.payment_intergration_system.backend.funding.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.techsensei.payment_intergration_system.backend.funding.events.FundingCompletedEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FundingNotificationListener {

    @EventListener
    public void handlePaymentNotification(FundingCompletedEvent event){

        log.info("Sending funding notification for fundingReference={}", event.getFundingReference());
    }

}

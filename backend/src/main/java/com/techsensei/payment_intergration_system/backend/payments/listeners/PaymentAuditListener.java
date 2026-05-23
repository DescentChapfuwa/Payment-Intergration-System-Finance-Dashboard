package com.techsensei.payment_intergration_system.backend.payments.listeners;

import com.techsensei.payment_intergration_system.backend.payments.events.PaymentCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentAuditListener {

    @EventListener
    public void handleAudit(PaymentCompletedEvent event){

        log.info("Creating audit entry paymentId={}", event.getPaymentId());

    }
}
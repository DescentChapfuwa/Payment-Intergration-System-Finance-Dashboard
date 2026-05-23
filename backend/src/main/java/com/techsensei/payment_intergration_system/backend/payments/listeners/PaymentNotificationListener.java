package com.techsensei.payment_intergration_system.backend.payments.listeners;

import com.techsensei.payment_intergration_system.backend.payments.events.PaymentCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentNotificationListener {

    @EventListener
    public void handlePaymentNotification(PaymentCompletedEvent event){

        log.info("Sending payment notification for paymentId={}", event.getPaymentId());
    }

}

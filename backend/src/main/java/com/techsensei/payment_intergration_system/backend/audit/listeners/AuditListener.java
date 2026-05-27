package com.techsensei.payment_intergration_system.backend.audit.listeners;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.techsensei.payment_intergration_system.backend.audit.entity.AuditLog;
import com.techsensei.payment_intergration_system.backend.audit.events.AuditEvent;
import com.techsensei.payment_intergration_system.backend.audit.repository.AuditRepository;
import com.techsensei.payment_intergration_system.backend.payments.events.PaymentCompletedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditListener {

    private final AuditRepository auditRepository;

    @EventListener
    public void handlePaymentCompleted(PaymentCompletedEvent event) {

        AuditLog logEntity = AuditLog.builder()
                .userId( event.getSenderId())
                .action( "PAYMENT_TRANSFER")
                .description("Transferred funds to user "+ event.getReceiverId())
                .createdAt( LocalDateTime.now())
                .build();

        auditRepository.save(logEntity);

        log.info("Audit log saved");
    }
}

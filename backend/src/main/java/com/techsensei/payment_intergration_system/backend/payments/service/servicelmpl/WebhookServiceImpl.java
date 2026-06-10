package com.techsensei.payment_intergration_system.backend.payments.service.servicelmpl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techsensei.payment_intergration_system.backend.funding.entity.FundingTransaction;
import com.techsensei.payment_intergration_system.backend.funding.enums.FundingStatus;
import com.techsensei.payment_intergration_system.backend.funding.events.FundingCompletedEvent;
import com.techsensei.payment_intergration_system.backend.funding.repository.FundingTransactionRepository;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaynowWebhookPayload;
import com.techsensei.payment_intergration_system.backend.payments.repository.PaymentRepository;
import com.techsensei.payment_intergration_system.backend.payments.service.WebhookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WebhookServiceImpl implements WebhookService {

        private final PaymentRepository paymentRepository;
        private final FundingTransactionRepository fundingTransactionRepository;
        private final ApplicationEventPublisher applicationEventPublisher;

        @Override
        @Transactional
        public void processWebhook(PaynowWebhookPayload payload) {

                log.info("Processing Paynow webhook reference={} status={}", payload.getReference(),
                                payload.getStatus());

                if (!"Paid".equalsIgnoreCase(payload.getStatus())) {
                        return;
                }

                UUID reference = UUID.fromString(payload.getReference());

                FundingTransaction transaction = fundingTransactionRepository.findByReference(reference)
                                .orElseThrow(() -> new RuntimeException("Funding transaction not found"));

                if (transaction.getStatus() == FundingStatus.SUCCESS) {
                        return;
                }

                transaction.setStatus(FundingStatus.SUCCESS);

                transaction.setCompletedAt(LocalDateTime.now());

                transaction.setPollUrl(payload.getPollUrl());

                fundingTransactionRepository.save(transaction);

                applicationEventPublisher.publishEvent(new FundingCompletedEvent(transaction.getUser().getId(),
                                transaction.getReference(), transaction.getAmount()));
        }
}

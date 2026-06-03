package com.techsensei.payment_intergration_system.backend.payments.service.servicelmpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techsensei.payment_intergration_system.backend.common.exception.ResourceNotFoundException;
import com.techsensei.payment_intergration_system.backend.payments.dto.WebhookPayload;
import com.techsensei.payment_intergration_system.backend.payments.entity.Payment;
import com.techsensei.payment_intergration_system.backend.payments.entity.PaymentStatus;
import com.techsensei.payment_intergration_system.backend.payments.repository.PaymentRepository;
import com.techsensei.payment_intergration_system.backend.payments.service.WebhookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WebhookServiceImpl implements WebhookService {

        private final PaymentRepository paymentRepository;

        @Override
        public void processWebhook(WebhookPayload payload) {

                Payment payment = paymentRepository.findByReference(payload.getPaymentReference())
                                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

                if ("SUCCESS".equalsIgnoreCase(payload.getStatus())) {
                        payment.setStatus(PaymentStatus.SUCCESS);
                } else {
                        payment.setStatus(PaymentStatus.FAILED);
                }

                paymentRepository.save(payment);
        }
}

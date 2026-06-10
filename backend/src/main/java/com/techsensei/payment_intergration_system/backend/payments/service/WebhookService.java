package com.techsensei.payment_intergration_system.backend.payments.service;

import com.techsensei.payment_intergration_system.backend.payments.dto.PaynowWebhookPayload;

public interface WebhookService {

    void processWebhook( PaynowWebhookPayload payload);
}

package com.techsensei.payment_intergration_system.backend.payments.service;

import com.techsensei.payment_intergration_system.backend.payments.dto.WebhookPayload;

public interface WebhookService {

    void processWebhook( WebhookPayload payload);
}

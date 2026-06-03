package com.techsensei.payment_intergration_system.backend.payments.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsensei.payment_intergration_system.backend.payments.dto.WebhookPayload;
import com.techsensei.payment_intergration_system.backend.payments.service.WebhookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;

    @PostMapping("/paynow") 
    public ResponseEntity<Void> paynowWebhook( @RequestBody WebhookPayload payload){

        webhookService.processWebhook(payload);

        return ResponseEntity.ok().build();
    }
}

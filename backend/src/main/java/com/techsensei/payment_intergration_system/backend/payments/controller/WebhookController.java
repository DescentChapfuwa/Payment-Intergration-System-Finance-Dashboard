package com.techsensei.payment_intergration_system.backend.payments.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techsensei.payment_intergration_system.backend.payments.dto.PaynowWebhookPayload;
import com.techsensei.payment_intergration_system.backend.payments.dto.WebhookPayload;
import com.techsensei.payment_intergration_system.backend.payments.service.WebhookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Webhooks")
@RestController
@RequestMapping("/webhooks")
@RequiredArgsConstructor
@Slf4j
public class WebhookController {

    private final WebhookService webhookService;

    @Operation(summary = "Paynow callback")
    @PostMapping(
        value = "/paynow",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
)
public ResponseEntity<Void> paynowWebhook(@RequestParam Map<String,String> payload){

    PaynowWebhookPayload webhookPayload = PaynowWebhookPayload.builder()
                    .reference(payload.get("reference"))
                    .paynowReference(payload.get("paynowreference"))
                    .amount(new BigDecimal(payload.get("amount")))
                    .status(payload.get("status"))
                    .pollUrl( payload.get("pollurl"))
                    .hash(payload.get("hash"))
                    .build();

    webhookService.processWebhook(webhookPayload);

    return ResponseEntity.ok().build();
}
}

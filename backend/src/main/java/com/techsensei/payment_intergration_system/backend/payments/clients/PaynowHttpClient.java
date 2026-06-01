package com.techsensei.payment_intergration_system.backend.payments.clients;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.techsensei.payment_intergration_system.backend.payments.dto.PaynowApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaynowHttpClient {

    private final RestClient restClient;

    public PaynowApiResponse initiatePayment(String email,BigDecimal amount){

        log.info("Calling Paynow API");

        /*
           Simulated HTTP call
         */

        return PaynowApiResponse
                .builder()
                .status("SUCCESS")
                .reference("PAYNOW-123")
                .message("External payment success")
                .build();
    }
}

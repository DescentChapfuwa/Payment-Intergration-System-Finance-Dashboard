package com.techsensei.payment_intergration_system.backend.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import zw.co.paynow.core.Paynow;

@Configuration
@EnableConfigurationProperties(PaynowProperties.class)
public class PaynowConfig {
    @Bean
    public Paynow paynow(PaynowProperties properties) {

        Paynow paynow = new Paynow(properties.getIntegrationId(), properties.getIntegrationKey());

        paynow.setResultUrl(properties.getResultUrl());

        paynow.setReturnUrl(properties.getReturnUrl());

        return paynow;
    }
}

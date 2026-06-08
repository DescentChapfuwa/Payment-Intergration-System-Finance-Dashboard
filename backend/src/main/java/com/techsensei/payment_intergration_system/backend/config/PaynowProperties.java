package com.techsensei.payment_intergration_system.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "paynow")
@Getter
@Setter
public class PaynowProperties {

    private String integrationId;
    
    private String merchantEmail;
    
    private String integrationKey;

    private String resultUrl;

    private String returnUrl;
}

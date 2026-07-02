package com.techsensei.payment_intergration_system.backend.notification.email.service;

import java.math.BigDecimal;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2))
    public void sendFundingEmail(String email, String reference, BigDecimal amount) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(email);

        mail.setSubject("Wallet Funding Successful");

        mail.setText("Reference: "
                + reference
                + "\nAmount: "
                + amount);

        mailSender.send(mail);
    }

    @Recover
    public void recover(Exception ex,String email, String reference, BigDecimal amount) {

        log.error("Email service currently unavailable", ex);

    }
}
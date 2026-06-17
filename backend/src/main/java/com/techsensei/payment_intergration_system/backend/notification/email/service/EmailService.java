package com.techsensei.payment_intergration_system.backend.notification.email.service;

import java.math.BigDecimal;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendFundingEmail(String email, String reference,BigDecimal amount){

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(email);

        mail.setSubject("Wallet Funding Successful");

        mail.setText("Reference: "
                        + reference
                        + "\nAmount: "
                        + amount
        );

        mailSender.send(mail);

    }
}
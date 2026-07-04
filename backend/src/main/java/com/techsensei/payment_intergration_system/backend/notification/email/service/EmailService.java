package com.techsensei.payment_intergration_system.backend.notification.email.service;

public interface EmailService {
    void sendEmail(String to, String subject,String body);
}

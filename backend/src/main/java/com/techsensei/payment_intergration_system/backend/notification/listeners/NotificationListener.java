package com.techsensei.payment_intergration_system.backend.notification.listeners;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.techsensei.payment_intergration_system.backend.notification.entity.Notification;
import com.techsensei.payment_intergration_system.backend.notification.repository.NotificationRepository;
import com.techsensei.payment_intergration_system.backend.payments.events.PaymentCompletedEvent;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    @EventListener
    public void handlePaymentCompleted(PaymentCompletedEvent event) {

        User sender = userRepository.findById(event.getSenderId()).orElse(null);

        User receiver = userRepository.findById(event.getReceiverId()).orElse(null);

        if (sender != null) {

            notificationRepository.save(Notification.builder()
                    .user(sender)
                    .title("Transfer Successful")
                    .message("Payment completed")
                    .isRead(false)
                    .createdAt(LocalDateTime.now())
                    .build());
        }

        if (receiver != null) {

            notificationRepository.save(Notification.builder()
                    .user(receiver)
                    .title("Money Received")
                    .message("Funds received")
                    .isRead(false)
                    .createdAt(LocalDateTime.now())
                    .build());
        }

    }
}
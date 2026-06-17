package com.techsensei.payment_intergration_system.backend.notification.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.techsensei.payment_intergration_system.backend.funding.events.FundingCompletedEvent;
import com.techsensei.payment_intergration_system.backend.notification.email.service.EmailService;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FundingNotificationEventListener {

    private final EmailService emailService;
    private final UserRepository userRepository;

    @EventListener
    public void handle(FundingCompletedEvent event){

        User user = userRepository.findById(event.getUserId()).orElseThrow();

        emailService.sendFundingEmail(user.getEmail(),event.getFundingReference().toString(), event.getAmount());

    }

}

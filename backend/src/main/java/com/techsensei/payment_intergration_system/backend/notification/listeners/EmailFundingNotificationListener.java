package com.techsensei.payment_intergration_system.backend.notification.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.techsensei.payment_intergration_system.backend.funding.events.FundingCompletedEvent;
import com.techsensei.payment_intergration_system.backend.notification.email.service.EmailService;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class EmailFundingNotificationListener {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @EventListener
    public void handle(FundingCompletedEvent event) {

        User user = userRepository.findById(event.getUserId())
                .orElseThrow();

        try {
            emailService.sendEmail(
                    user.getEmail(),
                    "Wallet Funding Successful",
                    """
                            Hello %s,

                            Your wallet has been funded successfully.

                            Reference: %s

                            Amount: %s

                            Thank you.
                            """.formatted(
                            user.getFullName(),
                            event.getFundingReference(),
                            event.getAmount()));
        } catch (Exception exception) {
            log.error("Sending email failed", exception);
        }

    }

}

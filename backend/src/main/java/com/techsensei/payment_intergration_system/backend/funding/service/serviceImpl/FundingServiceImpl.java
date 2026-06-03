package com.techsensei.payment_intergration_system.backend.funding.service.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.techsensei.payment_intergration_system.backend.funding.dto.FundingRequest;
import com.techsensei.payment_intergration_system.backend.funding.dto.FundingResponse;
import com.techsensei.payment_intergration_system.backend.funding.entity.FundingTransaction;
import com.techsensei.payment_intergration_system.backend.funding.enums.FundingStatus;
import com.techsensei.payment_intergration_system.backend.funding.events.FundingCompletedEvent;
import com.techsensei.payment_intergration_system.backend.funding.repository.FundingTransactionRepository;
import com.techsensei.payment_intergration_system.backend.funding.service.FundingService;
import com.techsensei.payment_intergration_system.backend.payments.dto.ProviderPaymentResponse;
import com.techsensei.payment_intergration_system.backend.payments.providers.PaymentProvider;
import com.techsensei.payment_intergration_system.backend.payments.providers.PaymentProviderFactory;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FundingServiceImpl implements FundingService {

    private final UserRepository userRepository;

    private final FundingTransactionRepository fundingtransactionRepository;

    private final PaymentProviderFactory paymentProviderFactory;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public FundingResponse initiateFunding(Long userId, FundingRequest request) {

        log.info("Initiating funding for user: {} with amount: {} and provider: {}", userId, request.getAmount(),
                request.getProvider());

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        FundingTransaction transaction = FundingTransaction.builder()
                .user(user)
                .amount(request.getAmount())
                .provider(request.getProvider())
                .status(FundingStatus.PENDING)
                .build();

        transaction = fundingtransactionRepository.save(transaction);

        PaymentProvider provider = paymentProviderFactory.getProvider(request.getProvider());

        ProviderPaymentResponse providerResponse = provider.processPayment(user.getEmail(), request.getAmount());

        transaction.setProviderReference(providerResponse.getProviderReference());

        transaction.setStatus(FundingStatus.SUCCESS);

        transaction.setCompletedAt(LocalDateTime.now());

        fundingtransactionRepository.save(transaction);

        applicationEventPublisher.publishEvent(

            new FundingCompletedEvent( transaction.getUser().getId(), transaction.getReference(),transaction.getAmount()
            )
        );

        return FundingResponse.builder()
                .reference(transaction.getReference())
                .status(transaction.getStatus())
                .providerReference(providerResponse.getProviderReference())
                .checkoutUrl(providerResponse.getCheckoutUrl())
                .message(providerResponse.getMessage())
                .build();

    }

}

package com.techsensei.payment_intergration_system.backend.funding.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techsensei.payment_intergration_system.backend.funding.dto.FundingRequest;
import com.techsensei.payment_intergration_system.backend.funding.dto.FundingResponse;
import com.techsensei.payment_intergration_system.backend.funding.dto.FundingTransactionDetails;
import com.techsensei.payment_intergration_system.backend.funding.entity.FundingTransaction;
import com.techsensei.payment_intergration_system.backend.funding.enums.FundingStatus;
import com.techsensei.payment_intergration_system.backend.funding.mapper.FundingTransactionMapper;
import com.techsensei.payment_intergration_system.backend.funding.repository.FundingTransactionRepository;
import com.techsensei.payment_intergration_system.backend.funding.service.FundingService;
import com.techsensei.payment_intergration_system.backend.payments.dto.ProviderPaymentResponse;
import com.techsensei.payment_intergration_system.backend.payments.entity.PaymentStatus;
import com.techsensei.payment_intergration_system.backend.payments.providers.PaymentProvider;
import com.techsensei.payment_intergration_system.backend.payments.providers.PaymentProviderFactory;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FundingServiceImpl implements FundingService {

        private final UserRepository userRepository;
        private final FundingTransactionRepository fundingTransactionRepository;
        private final PaymentProviderFactory paymentProviderFactory;

        @Override
        public FundingResponse initiateFunding(Long userId, FundingRequest request) {

                log.info(
                                "Initiating funding for user: {} with amount: {} and provider: {}",
                                userId,
                                request.getAmount(),
                                request.getProvider());

                User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

                // Create transaction in PENDING state
                FundingTransaction transaction = FundingTransaction.builder()
                                .user(user)
                                .amount(request.getAmount())
                                .provider(request.getProvider())
                                .status(FundingStatus.PENDING)
                                .build();

                transaction = fundingTransactionRepository.save(transaction);

                // Select provider
                PaymentProvider provider = paymentProviderFactory.getProvider(request.getProvider());

                // Initiate external payment
                ProviderPaymentResponse providerResponse = provider.processPayment(
                                transaction.getReference().toString(),
                                user.getEmail(),
                                request.getAmount());

                if (providerResponse.getStatus() == PaymentStatus.FAILED) {

                        throw new RuntimeException(providerResponse.getMessage());
                }

                // Save provider information
                transaction.setProviderReference(providerResponse.getProviderReference());

                // If you added pollUrl to FundingTransaction
                if (providerResponse.getPollUrl() != null) {
                        transaction.setPollUrl(providerResponse.getPollUrl());
                }

                fundingTransactionRepository.save(transaction);

                log.info(
                                "Funding transaction {} initiated successfully. Waiting for webhook confirmation.",
                                transaction.getReference());

                return FundingResponse.builder()
                                .reference(transaction.getReference())
                                .status(transaction.getStatus()) // PENDING
                                .providerReference(providerResponse.getProviderReference())
                                .checkoutUrl(providerResponse.getCheckoutUrl())
                                .message(providerResponse.getMessage())
                                .build();
        }

        @Override
        public List<FundingTransactionDetails> getAllTransactions(Long userld) {

                log.info("Retrieving All Transactiions from the database");

                return fundingTransactionRepository.findByUserIdOrderByStatus(userld)
                                .stream()
                                .map(FundingTransactionMapper::mapToResponse)
                                .toList();
        }
}

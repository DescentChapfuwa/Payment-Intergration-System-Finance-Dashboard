package com.techsensei.payment_intergration_system.backend.funding.service.servicelmpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import com.techsensei.payment_intergration_system.backend.funding.dto.FundingRequest;
import com.techsensei.payment_intergration_system.backend.funding.dto.FundingResponse;
import com.techsensei.payment_intergration_system.backend.funding.entity.FundingTransaction;
import com.techsensei.payment_intergration_system.backend.funding.enums.FundingStatus;
import com.techsensei.payment_intergration_system.backend.funding.events.FundingCompletedEvent;
import com.techsensei.payment_intergration_system.backend.funding.repository.FundingTransactionRepository;
import com.techsensei.payment_intergration_system.backend.funding.service.serviceImpl.FundingServiceImpl;
import com.techsensei.payment_intergration_system.backend.payments.dto.ProviderPaymentResponse;
import com.techsensei.payment_intergration_system.backend.payments.entity.PaymentStatus;
import com.techsensei.payment_intergration_system.backend.payments.providers.PaymentProvider;
import com.techsensei.payment_intergration_system.backend.payments.providers.PaymentProviderFactory;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class FundingServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FundingTransactionRepository fundingRepository;

    @Mock
    private PaymentProviderFactory providerFactory;

    @Mock
    private PaymentProvider paymentProvider;

    @Mock
    private ApplicationEventPublisher publisher;

    @InjectMocks
    private FundingServiceImpl fundingService;

    @Test
    void shouldInitiateFundingSuccessfully() {

        User user = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();

        FundingRequest request = FundingRequest.builder()
                .amount(BigDecimal.valueOf(20))
                .provider("PAYNOW")
                .build();

        FundingTransaction transaction = FundingTransaction.builder()
                .reference(UUID.randomUUID())
                .status(FundingStatus.PENDING)
                .build();

        ProviderPaymentResponse providerResponse = ProviderPaymentResponse.builder()
                .status(PaymentStatus.PENDING)
                .checkoutUrl("https://paynow.co.zw")
                .providerReference("ABC123")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(providerFactory.getProvider("PAYNOW")).thenReturn(paymentProvider);

        when(paymentProvider.processPayment(anyString(), anyString(), any(BigDecimal.class)))
                .thenReturn(providerResponse);

        when(fundingRepository.save(any())).thenReturn(transaction);

        FundingResponse response = fundingService.initiateFunding(1L, request);

        assertNotNull(response);

        verify(fundingRepository, times(2)).save(any());

        verify(paymentProvider).processPayment(anyString(), anyString(), any(BigDecimal.class));
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {

        FundingRequest request = FundingRequest.builder()
                .amount(BigDecimal.TEN)
                .provider("PAYNOW")
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> fundingService.initiateFunding(
                        1L,
                        request));

                    
    }
 
}

package com.techsensei.payment_intergration_system.backend.payments.service.serviceImpl;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;




import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.techsensei.payment_intergration_system.backend.funding.entity.FundingTransaction;
import com.techsensei.payment_intergration_system.backend.funding.enums.FundingStatus;
import com.techsensei.payment_intergration_system.backend.funding.repository.FundingTransactionRepository;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaynowWebhookPayload;
import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.payments.repository.WalletRepository;
import com.techsensei.payment_intergration_system.backend.payments.service.servicelmpl.WebhookServiceImpl;
import com.techsensei.payment_intergration_system.backend.users.entity.User;

@ExtendWith(MockitoExtension.class)
class WebhookServiceImplTest {

    @Mock
    private FundingTransactionRepository fundingRepository;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WebhookServiceImpl webhookService;

    @Test
    void shouldMarkFundingAsSuccessful() {

        UUID reference = UUID.randomUUID();

        User user = User.builder()
        .id(1L)
        .build();

        FundingTransaction transaction = FundingTransaction.builder()
                .reference(reference)
                .status(FundingStatus.SUCCESS)
                .user(user)
                .amount(BigDecimal.valueOf(20))
                .build();

        when(fundingRepository.findByReference(reference)).thenReturn(
                        Optional.of(transaction));


        PaynowWebhookPayload payload = PaynowWebhookPayload.builder()
                .reference(reference.toString())
                .status("Paid")
                .build();

        webhookService.processWebhook(payload);

    
        assertEquals(FundingStatus.SUCCESS,transaction.getStatus());
    }
}

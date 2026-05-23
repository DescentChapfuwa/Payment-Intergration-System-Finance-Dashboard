package com.techsensei.payment_intergration_system.backend.payments.listeners;

import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.payments.events.PaymentCompletedEvent;
import com.techsensei.payment_intergration_system.backend.payments.repository.WalletRepository;
import com.techsensei.payment_intergration_system.backend.payments.service.WalletCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WalletCacheListener {

    private final WalletRepository walletRepository;

    private final WalletCacheService walletCacheService;

    @EventListener
    public void updateBalanceCache(PaymentCompletedEvent event){

        Wallet senderWallet = walletRepository.findByUserIdForUpdate(event.getSenderId()).orElse(null);

        Wallet receiverWallet = walletRepository.findByUserIdForUpdate(event.getReceiverId()).orElse(null);

        if(senderWallet != null){walletCacheService.cacheBalance(event.getSenderId(), senderWallet.getBalance());}

        if(receiverWallet != null){

            walletCacheService.cacheBalance(event.getReceiverId(), receiverWallet.getBalance());
        }

        log.info("Wallet cache updated for sender={} receiver={}", event.getSenderId(), event.getReceiverId());
    }
}

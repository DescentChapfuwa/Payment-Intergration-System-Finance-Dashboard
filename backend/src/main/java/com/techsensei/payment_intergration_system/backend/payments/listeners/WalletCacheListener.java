package com.techsensei.payment_intergration_system.backend.payments.listeners;

import com.techsensei.payment_intergration_system.backend.payments.dto.WalletResponse;
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
        try{
            updateWalletCache(event.getSenderId());

            updateWalletCache(event.getReceiverId());

            log.info("Wallet cache updated");

        }catch(Exception ex){
            log.error("Cache update failed: {}", ex.getMessage());
        }
    }

    private void updateWalletCache(Long userId){

        Wallet wallet = walletRepository.findByUserIdForUpdate(userId).orElse(null);

        if(wallet == null){return;}

        WalletResponse response = WalletResponse.builder()
                        .walletId(wallet.getId())
                        .balance(wallet.getBalance())
                        .currency(wallet.getCurrency())
                        .build();

        walletCacheService.cacheWallet(userId, response);
    }
}

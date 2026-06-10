package com.techsensei.payment_intergration_system.backend.funding.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techsensei.payment_intergration_system.backend.common.exception.ResourceNotFoundException;
import com.techsensei.payment_intergration_system.backend.funding.events.FundingCompletedEvent;
import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.payments.repository.WalletRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class FundingWalletListener {

    private final WalletRepository walletRepository;
   
    @EventListener
    @Transactional
    public void handleFundingCompleted(FundingCompletedEvent event) {

        log.info("Updating the wallet balance for fundingReference={}", event.getFundingReference());

        Wallet wallet = walletRepository.findByUserIdForUpdate(event.getUserId())
        .orElseThrow(()-> new ResourceNotFoundException("Wallet not found"));

        wallet.setBalance(wallet.getBalance().add(event.getAmount()));

        walletRepository.save(wallet);

    }

}

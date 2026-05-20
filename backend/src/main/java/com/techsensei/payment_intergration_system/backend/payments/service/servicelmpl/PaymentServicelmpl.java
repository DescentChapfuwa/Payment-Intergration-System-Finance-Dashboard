package com.techsensei.payment_intergration_system.backend.payments.service.servicelmpl;

import com.techsensei.payment_intergration_system.backend.common.exception.ResourceNotFoundException;
import com.techsensei.payment_intergration_system.backend.common.exception.InvalidPaymentException;
import com.techsensei.payment_intergration_system.backend.common.exception.InsufficientBalanceException;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentRequest;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentResponse;
import com.techsensei.payment_intergration_system.backend.payments.entity.PaymentStatus;
import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.payments.repository.PaymentRepository;
import com.techsensei.payment_intergration_system.backend.payments.repository.TransactionRepository;
import com.techsensei.payment_intergration_system.backend.payments.repository.WalletRepository;
import com.techsensei.payment_intergration_system.backend.payments.service.PaymentService;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.payments.entity.Payment;
import com.techsensei.payment_intergration_system.backend.payments.entity.Transaction;
import com.techsensei.payment_intergration_system.backend.payments.entity.TransactionType;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServicelmpl  implements PaymentService {

    private final UserRepository userRepository;

    private final WalletRepository walletRepository;

    private final PaymentRepository paymentRepository;

    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public PaymentResponse sendMoney(Long senderId, PaymentRequest request) {

        log.info(
                "Payment initiated senderId={}, receiverId={}, amount={}",
                senderId,
                request.getReceiverId(),
                request.getAmount()
        );

        User sender = userRepository.findById(senderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));

        User receiver = userRepository.findById(request.getReceiverId())
                        .orElseThrow(() -> new ResourceNotFoundException("Receiver not found"));

        if(sender.getId().equals(receiver.getId())){

            throw new InvalidPaymentException("Cannot send money to yourself");

        }

        Wallet senderWallet = walletRepository
                        .findByUserId(senderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Sender wallet not found"));

        Wallet receiverWallet = walletRepository
                        .findByUserId(receiver.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Receiver wallet not found"));

        if(senderWallet.getBalance().compareTo(request.getAmount()) < 0){

            throw new InsufficientBalanceException("Insufficient balance");
        }

        Payment payment = Payment.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .amount(request.getAmount())
                        .status(PaymentStatus.PENDING)
                        .createdAt(LocalDateTime.now())
                        .build();

        paymentRepository.save(payment);

        senderWallet.setBalance(senderWallet.getBalance().subtract(request.getAmount()));

        receiverWallet.setBalance(receiverWallet.getBalance().add(request.getAmount()));

        walletRepository.save(senderWallet);

        walletRepository.save(receiverWallet);

        Transaction debit = Transaction.builder()
                        .wallet(senderWallet)
                        .amount(request.getAmount())
                        .type(TransactionType.DEBIT)
                        .createdAt(LocalDateTime.now())
                        .build();

        Transaction credit = Transaction.builder()
                        .wallet(receiverWallet)
                        .amount(request.getAmount())
                        .type(TransactionType.CREDIT)
                        .createdAt(LocalDateTime.now())
                        .build();

        transactionRepository.save(debit);

        transactionRepository.save(credit);

        payment.setStatus(PaymentStatus.SUCCESS);

        paymentRepository.save(payment);

        log.info("Payment completed paymentId={}", payment.getId());

        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .message("Transfer successful")
                .build();
    }
}

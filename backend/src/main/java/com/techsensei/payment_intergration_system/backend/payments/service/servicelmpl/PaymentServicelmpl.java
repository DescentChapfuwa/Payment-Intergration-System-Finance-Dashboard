package com.techsensei.payment_intergration_system.backend.payments.service.servicelmpl;

import com.techsensei.payment_intergration_system.backend.common.exception.ResourceNotFoundException;
import com.techsensei.payment_intergration_system.backend.common.exception.InvalidPaymentException;
import com.techsensei.payment_intergration_system.backend.common.exception.InsufficientBalanceException;
import com.techsensei.payment_intergration_system.backend.payments.dto.PagedResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentHistoryResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentRequest;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentResponse;
import com.techsensei.payment_intergration_system.backend.payments.entity.PaymentStatus;
import com.techsensei.payment_intergration_system.backend.payments.entity.Wallet;
import com.techsensei.payment_intergration_system.backend.payments.events.PaymentCompletedEvent;
import com.techsensei.payment_intergration_system.backend.payments.repository.PaymentRepository;
import com.techsensei.payment_intergration_system.backend.payments.repository.TransactionRepository;
import com.techsensei.payment_intergration_system.backend.payments.repository.WalletRepository;
import com.techsensei.payment_intergration_system.backend.payments.service.PaymentService;
import com.techsensei.payment_intergration_system.backend.payments.specification.PaymentSpecification;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.audit.events.AuditEvent;
import com.techsensei.payment_intergration_system.backend.payments.entity.Payment;
import com.techsensei.payment_intergration_system.backend.payments.entity.Transaction;
import com.techsensei.payment_intergration_system.backend.payments.entity.TransactionType;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServicelmpl implements PaymentService {

        private final UserRepository userRepository;

        private final WalletRepository walletRepository;

        private final PaymentRepository paymentRepository;

        private final TransactionRepository transactionRepository;

        private final ApplicationEventPublisher eventPublisher;

        private String generateReference() {

                return "PAY-" + UUID.randomUUID()
                                .toString()
                                .replace("-", "")
                                .substring(0, 16)
                                .toUpperCase();
        }

        @Override
        @Transactional
        public PaymentResponse sendMoney(String senderEmail, PaymentRequest request) {

                Payment existingPayment = paymentRepository.findByIdempotencyKey(request.getIdempotencyKey())
                                .orElse(null);

                if (existingPayment != null) {

                        log.info("Duplicate request detected");

                        return PaymentResponse
                                        .builder()
                                        .paymentId(existingPayment.getId())
                                        .reference(existingPayment.getReference())
                                        .amount(existingPayment.getAmount())
                                        .status(existingPayment.getStatus())
                                        .message("Existing payment returned")
                                        .build();
                }

                log.info("Payment initiated senderId={}, receiverId={}, amount={}",
                                senderEmail,
                                request.getReceiverId(),
                                request.getAmount());

                User sender = userRepository.findByEmail(senderEmail)
                                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));

                User receiver = userRepository.findById(request.getReceiverId())
                                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found"));

                if (sender.getId().equals(receiver.getId())) {

                        throw new InvalidPaymentException("Cannot send money to yourself");

                }

                Wallet senderWallet = walletRepository
                                .findByUserIdForUpdate(sender.getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Sender wallet not found"));

                Wallet receiverWallet = walletRepository
                                .findByUserIdForUpdate(receiver.getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Receiver wallet not found"));

                if (senderWallet.getBalance().compareTo(request.getAmount()) < 0) {

                        throw new InsufficientBalanceException("Insufficient balance");
                }

                Payment payment = Payment.builder()
                                .reference(generateReference())
                                .idempotencyKey(request.getIdempotencyKey())
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

                eventPublisher.publishEvent(
                                new PaymentCompletedEvent(
                                                payment.getId(),
                                                sender.getId(),
                                                receiver.getId(),
                                                request.getAmount()));

                log.info("Payment completed paymentId={}", payment.getId());

                return PaymentResponse.builder()
                                .reference(payment.getReference())
                                .paymentId(payment.getId())
                                .amount(payment.getAmount())
                                .status(payment.getStatus())
                                .message("Transfer successful")
                                .build();
        }

        @Override
        @Transactional(readOnly = true)
        public PagedResponse<PaymentHistoryResponse> getPaymentHistory(Long userId, PaymentStatus status,
                        String reference, BigDecimal minAmount, int page, int size) {

                Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

                Specification<Payment> spec = Specification
                                .where(PaymentSpecification.hasStatus(status))
                                .and(PaymentSpecification
                                                .hasReference(reference))
                                .and(PaymentSpecification
                                                .minAmount(minAmount))
                                .and((root, query, cb) -> cb.or(cb.equal(root.get("sender").get("id"), userId),
                                                cb.equal(root.get("receiver").get("id"), userId)));

                Page<Payment> paymentPage = paymentRepository.findAll(spec, pageable);

                List<PaymentHistoryResponse> content = paymentPage
                                .getContent()
                                .stream()
                                .map(payment -> PaymentHistoryResponse
                                                .builder()
                                                .reference(payment.getReference())
                                                .senderEmail(payment.getSender().getEmail())
                                                .receiverEmail(payment.getReceiver().getEmail())
                                                .amount(payment.getAmount())
                                                .status(payment.getStatus())
                                                .createdAt(payment.getCreatedAt())
                                                .build())
                                .toList();

                return PagedResponse
                                .<PaymentHistoryResponse>builder()
                                .content(content)
                                .page(paymentPage.getNumber())
                                .size(paymentPage.getSize())
                                .totalElements(paymentPage.getTotalElements())
                                .totalPages(paymentPage.getTotalPages())
                                .last(paymentPage.isLast())
                                .build();
        }

}

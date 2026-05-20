package com.techsensei.payment_intergration_system.backend.payments.service.servicelmpl;

import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentRequest;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentResponse;
import com.techsensei.payment_intergration_system.backend.payments.repository.PaymentRepository;
import com.techsensei.payment_intergration_system.backend.payments.repository.TransactionRepository;
import com.techsensei.payment_intergration_system.backend.payments.repository.WalletRepository;
import com.techsensei.payment_intergration_system.backend.payments.service.PaymentService;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServicelmpl  implements PaymentService {

    private final UserRepository userRepository;

    private final WalletRepository walletRepository;

    private final PaymentRepository paymentRepository;

    private final TransactionRepository transactionRepository;

    @Override
    public PaymentResponse sendMoney(Long senderId, PaymentRequest request) {
        return null;
    }
}

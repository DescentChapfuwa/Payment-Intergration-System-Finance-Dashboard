package com.techsensei.payment_intergration_system.backend.payments.service;

import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentRequest;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse sendMoney(Long senderId, PaymentRequest request);
}

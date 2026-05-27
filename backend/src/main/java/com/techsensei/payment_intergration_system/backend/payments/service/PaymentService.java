package com.techsensei.payment_intergration_system.backend.payments.service;

import java.math.BigDecimal;

import com.techsensei.payment_intergration_system.backend.payments.dto.PagedResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentHistoryResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentRequest;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentResponse;
import com.techsensei.payment_intergration_system.backend.payments.entity.PaymentStatus;

public interface PaymentService {

    PaymentResponse sendMoney(String senderEmail, PaymentRequest request);

    PagedResponse<PaymentHistoryResponse> getPaymentHistory(Long userId,PaymentStatus status,String reference,BigDecimal minAmount,int page,int size);
}

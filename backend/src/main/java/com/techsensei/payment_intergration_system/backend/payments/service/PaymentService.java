package com.techsensei.payment_intergration_system.backend.payments.service;

import com.techsensei.payment_intergration_system.backend.payments.dto.PagedResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentHistoryResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentRequest;
import com.techsensei.payment_intergration_system.backend.payments.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse sendMoney(String senderEmail, PaymentRequest request);

    PagedResponse<PaymentHistoryResponse> getPaymentHistory(Long userId, int page, int size);
}

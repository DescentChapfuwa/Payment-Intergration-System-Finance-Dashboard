package com.techsensei.payment_intergration_system.backend.payments.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.techsensei.payment_intergration_system.backend.payments.entity.Payment;
import com.techsensei.payment_intergration_system.backend.payments.entity.PaymentStatus;

public class PaymentSpecification {

    public static Specification<Payment> hasStatus(PaymentStatus status) {
        return (root, query, cb) -> status == null
                ? null
                : cb.equal(root.get("status"), status);
    }

    public static Specification<Payment> hasReference(String reference) {
        return (root, query, cb) -> reference == null
                ? null
                : cb.equal(root.get("reference"), reference);
    }

    public static Specification<Payment> minAmount(BigDecimal amount) {
        return (root, query, cb) -> amount == null
                ? null
                : cb.greaterThanOrEqualTo(root.get("amount"), amount);
    }

}

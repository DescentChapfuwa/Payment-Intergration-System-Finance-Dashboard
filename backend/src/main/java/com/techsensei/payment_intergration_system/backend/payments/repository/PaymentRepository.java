package com.techsensei.payment_intergration_system.backend.payments.repository;

import com.techsensei.payment_intergration_system.backend.payments.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long>,JpaSpecificationExecutor<Payment> {

    Page<Payment> findBySenderIdOrReceiverId(Long senderId, Long receiverId, Pageable pageable);

    Optional<Payment> findByIdempotencyKey(String idempotencyKey);

}

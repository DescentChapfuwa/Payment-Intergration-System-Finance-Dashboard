package com.techsensei.payment_intergration_system.backend.payments.repository;

import com.techsensei.payment_intergration_system.backend.payments.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

}

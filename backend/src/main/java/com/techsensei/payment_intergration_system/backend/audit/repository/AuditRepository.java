package com.techsensei.payment_intergration_system.backend.audit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techsensei.payment_intergration_system.backend.audit.entity.AuditLog;


public interface AuditRepository extends JpaRepository<AuditLog, Long> {
    
    Optional<AuditLog> findByEntityReference(String entityReference);

    Optional<AuditLog> findByUserId(Long userId);
}

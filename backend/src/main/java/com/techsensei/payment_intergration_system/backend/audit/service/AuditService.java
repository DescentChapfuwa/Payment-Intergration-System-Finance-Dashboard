package com.techsensei.payment_intergration_system.backend.audit.service;

import java.util.List;
import java.util.Optional;

import com.techsensei.payment_intergration_system.backend.audit.dto.AuditLogResponse;

public interface AuditService {

    void log(Long userId, String action, String entityType, String entityReference, String description);

    List<AuditLogResponse> getAuditLogHistory();  

    Optional<AuditLogResponse> getAuditLogByReference(String entityReference);

    Optional<AuditLogResponse> getAuditLogByUserId(Long userld);
}

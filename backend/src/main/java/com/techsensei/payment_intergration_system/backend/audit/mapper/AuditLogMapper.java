package com.techsensei.payment_intergration_system.backend.audit.mapper;

import com.techsensei.payment_intergration_system.backend.audit.dto.AuditLogResponse;
import com.techsensei.payment_intergration_system.backend.audit.entity.AuditLog;

public class AuditLogMapper {

    public static AuditLogResponse mapToResponse(AuditLog auditLog) {

        return AuditLogResponse.builder()
                .id(auditLog.getId())
                .action(auditLog.getAction())
                .description(auditLog.getDescription())
                .entityReference(auditLog.getEntityReference())
                .entityType(auditLog.getEntityType())
                .userId(auditLog.getUserId())
                .createdAt(auditLog.getCreatedAt())
                .build();
    }

}

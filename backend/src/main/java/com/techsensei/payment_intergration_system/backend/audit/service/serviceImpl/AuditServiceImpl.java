package com.techsensei.payment_intergration_system.backend.audit.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techsensei.payment_intergration_system.backend.audit.dto.AuditLogResponse;
import com.techsensei.payment_intergration_system.backend.audit.entity.AuditLog;
import com.techsensei.payment_intergration_system.backend.audit.mapper.AuditLogMapper;
import com.techsensei.payment_intergration_system.backend.audit.repository.AuditRepository;
import com.techsensei.payment_intergration_system.backend.audit.service.AuditService;
import com.techsensei.payment_intergration_system.backend.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditServiceImpl implements AuditService {

        private final AuditRepository repository;

        @Override
        public void log(
                        Long userId,
                        String action,
                        String entityType,
                        String entityReference,
                        String description) {

                AuditLog audit = AuditLog.builder()
                                .userId(userId)
                                .action(action)
                                .entityType(entityType)
                                .entityReference(entityReference)
                                .description(description)
                                .build();

                repository.save(audit);

                log.info("Audit log created action={} reference={}",
                                action,
                                entityReference);
        }

        @Override
        public List<AuditLogResponse> getAuditLogHistory() {
                log.info("Retrieving All Audit Logs from the database");

                return repository.findAll()
                                .stream()
                                .map(AuditLogMapper::mapToResponse)
                                .toList();
        }

        @Override
        public Optional<AuditLogResponse> getAuditLogByReference(String entityReference) {

                AuditLog auditLog = repository.findByEntityReference(entityReference)
                                .orElseThrow(() -> new ResourceNotFoundException("Audit Log with entity reference:"
                                                + entityReference + "was not found"));

                log.info("Audit Log retrieved successfully:{}", entityReference);

                return Optional.of(AuditLogMapper.mapToResponse(auditLog));
        }

        @Override
        public Optional<AuditLogResponse> getAuditLogByUserId(Long userld) {

                AuditLog auditLog = repository.findByUserId(userld)
                                .orElseThrow(() -> new ResourceNotFoundException("Audit Log with user ld:"
                                                + userld + "was not found"));

                log.info("Audit Log retrieved successfully:{}", userld);

                return Optional.of(AuditLogMapper.mapToResponse(auditLog));
        }
}
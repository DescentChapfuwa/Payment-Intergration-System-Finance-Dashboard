package com.techsensei.payment_intergration_system.backend.audit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsensei.payment_intergration_system.backend.audit.dto.AuditLogResponse;
import com.techsensei.payment_intergration_system.backend.audit.service.AuditService;
import com.techsensei.payment_intergration_system.backend.users.dto.UserResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Transactions")
@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AuditLogResponse>> getAllAuditLogs() {

        return ResponseEntity.ok(
                service.getAuditLogHistory());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("user/{userld}")
    public ResponseEntity<Optional<AuditLogResponse>> getUser( @PathVariable Long userld) {

        return ResponseEntity.ok(
                service.getAuditLogByUserId(userld));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("reference/{entityReference}")
    public ResponseEntity<Optional<AuditLogResponse>> getUser( @PathVariable String entityReference) {

        return ResponseEntity.ok(service.getAuditLogByReference(entityReference));
    }

}

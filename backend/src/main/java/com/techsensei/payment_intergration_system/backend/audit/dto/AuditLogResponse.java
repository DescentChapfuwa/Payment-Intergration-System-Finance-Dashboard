package com.techsensei.payment_intergration_system.backend.audit.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {

    private Long id;

    private Long userId;

    private String action;

    private String entityType;

    private String entityReference;

    private String description;

    private LocalDateTime createdAt;

}

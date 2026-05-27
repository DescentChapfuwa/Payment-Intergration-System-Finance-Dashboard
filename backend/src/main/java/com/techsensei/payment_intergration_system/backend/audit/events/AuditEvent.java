package com.techsensei.payment_intergration_system.backend.audit.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuditEvent {
    private Long userId;

    private String action;

    private String description;
}

package com.techsensei.payment_intergration_system.backend.common.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int status;

    private String message;

    private LocalDateTime timestamp;

    private Map<String, String> errors;

    private String error;

    private String path;
}

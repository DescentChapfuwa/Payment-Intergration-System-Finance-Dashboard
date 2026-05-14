package com.techsensei.payment_intergration_system.backend.users.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;
}

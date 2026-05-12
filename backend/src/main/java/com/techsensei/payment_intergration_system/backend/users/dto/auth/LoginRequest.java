package com.techsensei.payment_intergration_system.backend.users.dto.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}

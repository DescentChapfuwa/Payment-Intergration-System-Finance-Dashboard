package com.techsensei.payment_intergration_system.backend.users.dto;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String fullName;
    private String email;
    private String password;
}

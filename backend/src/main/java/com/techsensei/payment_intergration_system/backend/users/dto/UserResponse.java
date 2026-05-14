package com.techsensei.payment_intergration_system.backend.users.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String fullName;

    private String email;

    private String role;
}



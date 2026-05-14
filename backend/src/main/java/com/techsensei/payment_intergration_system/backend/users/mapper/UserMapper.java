package com.techsensei.payment_intergration_system.backend.users.mapper;

import com.techsensei.payment_intergration_system.backend.users.dto.UserResponse;
import com.techsensei.payment_intergration_system.backend.users.entity.User;

public class UserMapper {

    public static UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
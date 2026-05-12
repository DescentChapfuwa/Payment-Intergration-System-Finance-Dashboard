package com.techsensei.payment_intergration_system.backend.users.service;

import com.techsensei.payment_intergration_system.backend.users.dto.CreateUserRequest;
import com.techsensei.payment_intergration_system.backend.users.dto.UserResponse;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
}

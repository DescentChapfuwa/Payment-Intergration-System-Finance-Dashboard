package com.techsensei.payment_intergration_system.backend.users.service;

import com.techsensei.payment_intergration_system.backend.users.dto.CreateUserRequest;
import com.techsensei.payment_intergration_system.backend.users.dto.UpdateUserRequest;
import com.techsensei.payment_intergration_system.backend.users.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(
            Long id,
            UpdateUserRequest request
    );

    void deleteUser(Long id);
}

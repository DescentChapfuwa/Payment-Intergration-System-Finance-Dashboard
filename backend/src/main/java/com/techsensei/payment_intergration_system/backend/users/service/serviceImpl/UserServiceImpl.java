package com.techsensei.payment_intergration_system.backend.users.service.serviceImpl;

import com.techsensei.payment_intergration_system.backend.users.dto.CreateUserRequest;
import com.techsensei.payment_intergration_system.backend.users.dto.UserResponse;
import com.techsensei.payment_intergration_system.backend.users.entity.Role;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;
import com.techsensei.payment_intergration_system.backend.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public UserResponse createUser(CreateUserRequest request) {

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().name())
                .build();
    }
}

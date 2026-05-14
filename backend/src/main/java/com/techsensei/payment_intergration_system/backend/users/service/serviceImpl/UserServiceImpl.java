package com.techsensei.payment_intergration_system.backend.users.service.serviceImpl;

import com.techsensei.payment_intergration_system.backend.common.exception.BadRequestException;
import com.techsensei.payment_intergration_system.backend.common.exception.ResourceNotFoundException;
import com.techsensei.payment_intergration_system.backend.users.dto.CreateUserRequest;
import com.techsensei.payment_intergration_system.backend.users.dto.UpdateUserRequest;
import com.techsensei.payment_intergration_system.backend.users.dto.UserResponse;
import com.techsensei.payment_intergration_system.backend.users.entity.Role;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.users.mapper.UserMapper;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;
import com.techsensei.payment_intergration_system.backend.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        if (repository.findByEmail(request.getEmail()) != null) {
            throw new BadRequestException(
                    "Email already exists"
            );
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(Role.USER)
                .build();

        repository.save(user);

        return UserMapper.mapToResponse(user);
    }

    @Override
    public UserResponse getUserById(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        return UserMapper.mapToResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return repository.findAll()
                .stream()
                .map(UserMapper::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(
            Long id,
            UpdateUserRequest request
    ) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());

        repository.save(user);

        return UserMapper.mapToResponse(user);
    }

    @Override
    public void deleteUser(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        repository.delete(user);
    }
}


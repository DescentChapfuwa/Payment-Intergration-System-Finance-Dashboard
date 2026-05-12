package com.techsensei.payment_intergration_system.backend.users.service.serviceImpl;

import com.techsensei.payment_intergration_system.backend.security.jwt.JwtService;
import com.techsensei.payment_intergration_system.backend.users.dto.auth.AuthResponse;
import com.techsensei.payment_intergration_system.backend.users.dto.auth.LoginRequest;
import com.techsensei.payment_intergration_system.backend.users.dto.auth.RegisterRequest;
import com.techsensei.payment_intergration_system.backend.users.entity.Role;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;
import com.techsensei.payment_intergration_system.backend.users.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        repository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = repository.findByEmail(request.getEmail()).orElseThrow();

        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!matches) throw new RuntimeException("Invalid Credentials");

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }
}

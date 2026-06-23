package com.techsensei.payment_intergration_system.backend.users.controller;

import com.techsensei.payment_intergration_system.backend.users.dto.auth.AuthResponse;
import com.techsensei.payment_intergration_system.backend.users.dto.auth.LoginRequest;
import com.techsensei.payment_intergration_system.backend.users.dto.auth.RegisterRequest;
import com.techsensei.payment_intergration_system.backend.users.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication",description = "User registration and login")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register User")
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request){return authService.register(request);}

    @Operation(summary = "Login")
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){return authService.login(request);}
}

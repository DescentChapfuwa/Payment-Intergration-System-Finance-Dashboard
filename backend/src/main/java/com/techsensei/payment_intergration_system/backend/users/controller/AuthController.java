package com.techsensei.payment_intergration_system.backend.users.controller;

import com.techsensei.payment_intergration_system.backend.users.dto.auth.AuthResponse;
import com.techsensei.payment_intergration_system.backend.users.dto.auth.LoginRequest;
import com.techsensei.payment_intergration_system.backend.users.dto.auth.RegisterRequest;
import com.techsensei.payment_intergration_system.backend.users.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request){return authService.register(request);}

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){return authService.login(request);}
}

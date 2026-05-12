package com.techsensei.payment_intergration_system.backend.users.service;

import com.techsensei.payment_intergration_system.backend.users.dto.auth.AuthResponse;
import com.techsensei.payment_intergration_system.backend.users.dto.auth.LoginRequest;
import com.techsensei.payment_intergration_system.backend.users.dto.auth.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}

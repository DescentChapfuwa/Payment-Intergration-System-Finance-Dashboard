package com.techsensei.payment_intergration_system.backend.users.controller;

import com.techsensei.payment_intergration_system.backend.users.dto.CreateUserRequest;
import com.techsensei.payment_intergration_system.backend.users.dto.UserResponse;
import com.techsensei.payment_intergration_system.backend.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    @PostMapping
    public UserResponse createUser(
            @RequestBody CreateUserRequest request
    ) {
        return userService.createUser(request);
    }
}

package com.techsensei.payment_intergration_system.backend.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techsensei.payment_intergration_system.backend.common.exception.ResourceNotFoundException;
import com.techsensei.payment_intergration_system.backend.notification.dto.NotificationResponse;
import com.techsensei.payment_intergration_system.backend.notification.dto.UnreadCountResponse;
import com.techsensei.payment_intergration_system.backend.notification.service.NotificationService;
import com.techsensei.payment_intergration_system.backend.payments.dto.PagedResponse;
import com.techsensei.payment_intergration_system.backend.users.entity.User;
import com.techsensei.payment_intergration_system.backend.users.repository.UserRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationService notificationService;

    private final UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<PagedResponse<NotificationResponse>> getNotifications( Authentication authentication,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size){

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return ResponseEntity.ok(notificationService.getNotifications(user.getId(),page,size));
    }

    @PatchMapping("/{id}/read")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Void>markAsRead( @PathVariable Long id, Authentication authentication){

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        notificationService.markAsRead(id,user.getId());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/unread-count")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UnreadCountResponse> getUnreadCount( Authentication authentication){

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return ResponseEntity.ok(notificationService .getUnreadCount( user.getId()));
    }
}

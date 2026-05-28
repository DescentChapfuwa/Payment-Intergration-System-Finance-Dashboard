package com.techsensei.payment_intergration_system.backend.notification.entity;

import java.time.LocalDateTime;

import com.techsensei.payment_intergration_system.backend.users.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String title;

    private String message;

    private boolean isRead;

    private LocalDateTime createdAt;
}
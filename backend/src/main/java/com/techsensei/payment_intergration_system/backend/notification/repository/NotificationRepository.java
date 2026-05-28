package com.techsensei.payment_intergration_system.backend.notification.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techsensei.payment_intergration_system.backend.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification,Long>{

    Page<Notification>findByUserIdOrderByCreatedAtDesc(Long userId,Pageable pageable);

    @Query("""
       SELECT COUNT(n)
       FROM Notification n
       WHERE n.user.id = :userId
       AND n.isRead = FALSE
       """)
    long countByUserIdAndReadFalse(Long userId);

}

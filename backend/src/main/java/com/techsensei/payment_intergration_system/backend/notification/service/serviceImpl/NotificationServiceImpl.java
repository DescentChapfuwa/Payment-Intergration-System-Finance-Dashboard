package com.techsensei.payment_intergration_system.backend.notification.service.serviceImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techsensei.payment_intergration_system.backend.common.exception.ResourceNotFoundException;
import com.techsensei.payment_intergration_system.backend.notification.dto.NotificationResponse;
import com.techsensei.payment_intergration_system.backend.notification.dto.UnreadCountResponse;
import com.techsensei.payment_intergration_system.backend.notification.entity.Notification;
import com.techsensei.payment_intergration_system.backend.notification.repository.NotificationRepository;
import com.techsensei.payment_intergration_system.backend.notification.service.NotificationService;
import com.techsensei.payment_intergration_system.backend.payments.dto.PagedResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<NotificationResponse> getNotifications(Long userId,int page,int size){

        Pageable pageable =
                PageRequest.of(page,size,Sort.by(
                                Sort.Direction.DESC,
                                "createdAt"
                        )
                );

        Page<Notification> notificationPage = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId,pageable);

        List<NotificationResponse> content = notificationPage
                .getContent()
                .stream()
                .map(notification ->NotificationResponse
                        .builder()
                        .id(notification.getId())
                        .title(notification.getTitle())
                        .message(notification.getMessage())
                        .isRead(notification.isRead())
                        .createdAt(notification.getCreatedAt())
                        .build())
                .toList();

        return PagedResponse
                .<NotificationResponse>builder()
                .content(content)
                .page(notificationPage.getNumber())
                .size(notificationPage.getSize())
                .totalElements(notificationPage.getTotalElements())
                .totalPages(notificationPage.getTotalPages())
                .last(notificationPage.isLast())
                .build();
    }

    @Override
    public void markAsRead(Long notificationId, Long userId){
        Notification notification = notificationRepository
                .findById(notificationId)
                .orElseThrow(()-> new ResourceNotFoundException("Notification not found"));

        if(!notification.getUser().getId().equals(userId)){
                throw new RuntimeException("Unauthorized access");
        }

        notification.setRead(true);

        notificationRepository.save(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public UnreadCountResponse getUnreadCount(Long userId){

        long count = notificationRepository.countByUserIdAndReadFalse(userId);

        return UnreadCountResponse
                .builder()
                .unreadCount(count)
                .build();
    }
}

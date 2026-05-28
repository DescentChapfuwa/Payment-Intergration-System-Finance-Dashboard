package com.techsensei.payment_intergration_system.backend.notification.service;

import com.techsensei.payment_intergration_system.backend.notification.dto.NotificationResponse;
import com.techsensei.payment_intergration_system.backend.notification.dto.UnreadCountResponse;
import com.techsensei.payment_intergration_system.backend.payments.dto.PagedResponse;

public interface NotificationService {

    PagedResponse<NotificationResponse> getNotifications(Long userId, int page, int size);

    void markAsRead(Long notificationId, Long userId);

    UnreadCountResponse getUnreadCount(Long userId);

}

package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.response.NotificationResponse;
import com.tinjaku.model.Notification;

@Component
public class NotificationMapper {
    
    public NotificationResponse toResponse(Notification notification){
        
        return new NotificationResponse(notification.getNotificationId(),
                                        notification.getMessage(),
                                        notification.isRead(),
                                        notification.getCreatedAt());
    }
}
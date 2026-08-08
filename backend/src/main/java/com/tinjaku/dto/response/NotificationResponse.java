package com.tinjaku.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    
    private Long notificationId;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;
}
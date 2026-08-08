package com.tinjaku.service;

import org.springframework.stereotype.Service;

import com.tinjaku.mapper.NotificationMapper;
import com.tinjaku.repository.NotificationRepository;

@Service
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public NotificationService(NotificationRepository notificationRepository, NotificationMapper notificationMapper){
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }
}
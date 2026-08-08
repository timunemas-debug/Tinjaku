package com.tinjaku.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinjaku.dto.response.NotificationResponse;
import com.tinjaku.security.SecurityService;
import com.tinjaku.service.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    
    private final NotificationService notificationService;
    private final SecurityService securityService;

    public NotificationController(NotificationService notificationService, SecurityService securityService){
        this.notificationService = notificationService;
        this.securityService = securityService;
    }

    @GetMapping
    public List<NotificationResponse> getMyNotifiation(){

        Long userId = securityService.getCurrentUserId();

        return notificationService.getMyNotifications(userId);
    }

    @GetMapping("/unread")
    public List<NotificationResponse> getUnreadNotifications(){

        Long userId = securityService.getCurrentUserId();

        return notificationService.getUnreadNotifications(userId);
    }

    @GetMapping("/has-unread")
    public boolean getHasUnreadNotification(){

        Long userId = securityService.getCurrentUserId();

        return notificationService.hasUnreadNotifications(userId);
    }

    @PutMapping("/{notificationId}/read")
    public NotificationResponse markAsRead(@PathVariable Long notificationId){

        Long userId = securityService.getCurrentUserId();

        return notificationService.markAsRead(notificationId, userId);
    }

    @PutMapping("/read-all")
    public void markAllRead(){

        Long userId = securityService.getCurrentUserId();

        notificationService.markAllRead(userId);
    }
}
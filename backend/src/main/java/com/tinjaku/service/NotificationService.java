package com.tinjaku.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tinjaku.dto.response.NotificationResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.NotificationMapper;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.Notification;
import com.tinjaku.model.User;
import com.tinjaku.repository.NotificationRepository;

import jakarta.transaction.Transactional;

@Service
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserService userService;
    private final MitraService mitraService;

    public NotificationService(NotificationRepository notificationRepository, NotificationMapper notificationMapper, UserService userService, MitraService mitraService){
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.userService = userService;
        this.mitraService = mitraService;
    }

    @Async
    public void sendNotification(Long userId, String message){

        User user = userService.getUserById(userId);

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        notification.setUser(user);

        notificationRepository.save(notification);
    }

    @Async
    public void sendNotificationMitra(Long mitraId, String message){

        Mitra mitra = mitraService.getMitraById(mitraId);

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        notification.setMitra(mitra);

        notificationRepository.save(notification);
    }

    public List<NotificationResponse> getMyNotifications(Long userId){
        return notificationRepository
                .findByUserUserId(userId)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    public List<NotificationResponse> getUnreadNotifications(Long userId){

        return notificationRepository
                .findByUserUserIdAndIsRead(userId, false)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    public boolean hasUnreadNotifications(Long userId){
        
        return notificationRepository.existsByUserUserIdAndIsRead(userId, false);
    }

    @Transactional
    public NotificationResponse markAsRead(Long notificationId, Long userId){
        
        Notification notification = notificationRepository.findById(notificationId)
                        .orElseThrow(() -> new ResourceNotFound("Notification tidak ditemukan!"));

        if(!notification.getUser().getUserId().equals(userId)){
            throw new BadRequestException("Notification bukan milik user!");
        }

        notification.setRead(true);

        return notificationMapper.toResponse(notificationRepository.save(notification));
    }

    @Transactional
    public void markAllRead(Long userId){

        List<Notification> notifications = notificationRepository.findByUserUserIdAndIsRead(userId, false);

        notifications.forEach(notification -> notification.setRead(true));

        notificationRepository.saveAll(notifications);
    }
}
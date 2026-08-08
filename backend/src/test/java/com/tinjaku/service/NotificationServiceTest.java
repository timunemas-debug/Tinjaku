package com.tinjaku.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tinjaku.dto.response.NotificationResponse;
import com.tinjaku.mapper.NotificationMapper;
import com.tinjaku.model.Notification;
import com.tinjaku.model.User;
import com.tinjaku.repository.NotificationRepository;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    
    @Mock
    NotificationRepository notificationRepository;

    @Mock
    NotificationMapper notificationMapper;

    @Mock
    UserService userService;

    @InjectMocks
    NotificationService notificationService;

    @Test
    public void shouldSendNotification(){

        User user = new User();
        user.setUserId(1L);

        Notification notification = new Notification();
        notification.setNotificationId(2L);
        notification.setRead(false);
        notification.setMessage("Test");

        NotificationResponse response = new NotificationResponse();
        response.setNotificationId(2L);
        response.setRead(false);
        response.setMessage("Test");

        when(userService.getUserById(1L))
                .thenReturn(user);

        when(notificationMapper.toResponse(notification))
                .thenReturn(response);

        when(notificationRepository.save(any(Notification.class)))
                .thenReturn(notification);

        NotificationResponse result = notificationService.sendNotification(1L, "Test");

        assertEquals(2L, result.getNotificationId());
        assertEquals("Test", result.getMessage());

        verify(userService).getUserById(1L);
        verify(notificationMapper).toResponse(notification);
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    public void shouldGetMyNotification(){

        User user = new User();
        user.setUserId(1L);

        Notification notification = new Notification();
        notification.setNotificationId(2L);
        notification.setMessage("Test");
        notification.setRead(false);

        NotificationResponse response = new NotificationResponse();
        response.setNotificationId(2L);
        response.setMessage("Test");
        response.setRead(false);

        when(notificationRepository.findByUserUserId(1L))
                .thenReturn(List.of(notification));

        when(notificationMapper.toResponse(notification))
                .thenReturn(response);

        List<NotificationResponse> result = notificationService.getMyNotifications(1L);

        assertEquals(2L, result.get(0).getNotificationId());
        assertEquals("Test", result.get(0).getMessage());

        verify(notificationRepository).findByUserUserId(1L);
        verify(notificationMapper).toResponse(notification);
    }

    @Test
    public void shouldGetUnreadNotification(){

        User user = new User();
        user.setUserId(1L);

        Notification notification = new Notification();
        notification.setNotificationId(2L);
        notification.setMessage("Test");
        notification.setRead(false);

        NotificationResponse response = new NotificationResponse();
        response.setNotificationId(2L);
        response.setMessage("Test");
        response.setRead(false);

        when(notificationRepository.findByUserUserIdAndIsRead(1L, false))
                .thenReturn(List.of(notification));

        when(notificationMapper.toResponse(notification))
                .thenReturn(response);

        List<NotificationResponse> result = notificationService.getUnreadNotifications(1L);

        assertEquals(2L, result.get(0).getNotificationId());
        assertEquals("Test", result.get(0).getMessage());

        verify(notificationRepository).findByUserUserIdAndIsRead(1L, false);
        verify(notificationMapper).toResponse(notification);
    }

    @Test
    public void shouldGetHasUnreadNotifications(){

        User user = new User();
        user.setUserId(1L);

        when(notificationRepository.existsByUserUserIdAndIsRead(1L, false))
                .thenReturn(true);

        boolean result = notificationService.hasUnreadNotifications(1L);

        assertTrue(result);

        verify(notificationRepository).existsByUserUserIdAndIsRead(1L, false);
    }

    @Test
    public void shouldMarkAsRead(){

        User user = new User();
        user.setUserId(1L);

        Notification notification = new Notification();
        notification.setNotificationId(2L);
        notification.setMessage("Test");
        notification.setRead(false);
        notification.setUser(user);

        NotificationResponse response = new NotificationResponse();
        response.setNotificationId(2L);
        response.setMessage("Test");
        response.setRead(false);

        when(notificationRepository.findById(2L))
                .thenReturn(Optional.of(notification));

        when(notificationMapper.toResponse(notification))
                .thenReturn(response);
        
        when(notificationRepository.save(any(Notification.class)))
                .thenReturn(notification);

        NotificationResponse result = notificationService.markAsRead(2L, 1L);

        assertEquals(2L, result.getNotificationId());
        assertEquals("Test", result.getMessage());
        assertTrue(notification.isRead());

        verify(notificationRepository).findById(2L);
        verify(notificationMapper).toResponse(notification);
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    public void shouldGetMarkAllRead(){

        User user = new User();
        user.setUserId(1L);

        Notification notification = new Notification();
        notification.setNotificationId(2L);
        notification.setMessage("Test");
        notification.setRead(false);
        notification.setUser(user);

        List<Notification> notifications = List.of(notification);

        when(notificationRepository.findByUserUserIdAndIsRead(1L, false))
                .thenReturn(List.of(notification));

        when(notificationRepository.saveAll(notifications))
                .thenReturn(notifications);

        notificationService.markAllRead(1L);

        assertTrue(notification.isRead());

        verify(notificationRepository).findByUserUserIdAndIsRead(1L, false);
        verify(notificationRepository).saveAll(notifications);
    }
}
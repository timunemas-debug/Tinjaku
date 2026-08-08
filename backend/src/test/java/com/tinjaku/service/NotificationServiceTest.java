package com.tinjaku.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
}
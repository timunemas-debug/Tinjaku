package com.tinjaku.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tinjaku.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
    
    boolean existsByUserUserIdAndIsRead(Long userId, boolean isRead);
    List<Notification> findByUserUserId(Long userId);
    List<Notification> findByUserUserIdAndIsRead(Long userId, boolean isRead);
}
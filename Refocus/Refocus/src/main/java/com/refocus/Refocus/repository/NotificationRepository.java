package com.refocus.Refocus.repository;

import com.refocus.Refocus.model.Notification;
import com.refocus.Refocus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(User user);
}

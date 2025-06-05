package com.refocus.Refocus.Service;

import com.refocus.Refocus.model.Notification;
import com.refocus.Refocus.model.User;
import com.refocus.Refocus.repository.NotificationRepository;
import com.refocus.Refocus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public Notification createNotification(String userEmail, String title, String message) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Notification notification = Notification.builder()
                .title(title)
                .message(message)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        return notificationRepository.save(notification);
    }

    public List<Notification> getNotifications(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return notificationRepository.findByUser(user);
    }

    public Notification markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));

        notification.setRead(true);
        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}

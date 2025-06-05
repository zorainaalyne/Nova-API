package com.refocus.Refocus.controller;

import com.refocus.Refocus.model.Notification;
import com.refocus.Refocus.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @RequestBody Map<String, String> request,
            Principal principal) {

        String title = request.get("title");
        String message = request.get("message");

        return ResponseEntity.ok(
                notificationService.createNotification(principal.getName(), title, message)
        );
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications(Principal principal) {
        return ResponseEntity.ok(notificationService.getNotifications(principal.getName()));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}

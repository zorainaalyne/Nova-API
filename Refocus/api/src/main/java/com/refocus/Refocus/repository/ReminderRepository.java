package com.refocus.Refocus.repository;

import com.refocus.Refocus.model.Reminder;
import com.refocus.Refocus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByUser(User user);

    List<Reminder> findByUserAndRemindAtBetween(User user, LocalDateTime start, LocalDateTime end);
}

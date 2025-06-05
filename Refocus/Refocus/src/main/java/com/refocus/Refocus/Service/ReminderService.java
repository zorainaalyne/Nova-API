package com.refocus.Refocus.Service;

import com.refocus.Refocus.model.Reminder;
import com.refocus.Refocus.model.User;
import com.refocus.Refocus.repository.ReminderRepository;
import com.refocus.Refocus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;

    public Reminder createReminder(String userEmail, Reminder reminder) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        reminder.setUser(user);
        reminder.setCompleted(false);

        return reminderRepository.save(reminder);
    }

    public List<Reminder> getReminders(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return reminderRepository.findByUser(user);
    }

    public Optional<Reminder> getReminderById(Long id) {
        return reminderRepository.findById(id);
    }

    public Reminder updateReminder(Long id, Reminder updatedReminder) {
        return reminderRepository.findById(id)
                .map(reminder -> {
                    reminder.setTitle(updatedReminder.getTitle());
                    reminder.setDescription(updatedReminder.getDescription());
                    reminder.setRemindAt(updatedReminder.getRemindAt());
                    reminder.setCompleted(updatedReminder.isCompleted());
                    return reminderRepository.save(reminder);
                })
                .orElseThrow(() -> new RuntimeException("Lembrete não encontrado"));
    }

    public void deleteReminder(Long id) {
        reminderRepository.deleteById(id);
    }
}

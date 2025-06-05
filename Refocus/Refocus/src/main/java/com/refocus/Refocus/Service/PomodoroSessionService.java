package com.refocus.Refocus.Service;

import com.refocus.Refocus.model.PomodoroSession;
import com.refocus.Refocus.model.User;
import com.refocus.Refocus.repository.PomodoroSessionRepository;
import com.refocus.Refocus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PomodoroSessionService {

    private final PomodoroSessionRepository pomodoroSessionRepository;
    private final UserRepository userRepository;

    public PomodoroSession startSession(String userEmail, int focusMinutes, int shortBreakMinutes, int longBreakMinutes, int cycles) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se já tem uma sessão ativa
        pomodoroSessionRepository.findByUserAndIsActiveTrue(user).ifPresent(session -> {
            throw new RuntimeException("Já existe uma sessão ativa.");
        });

        PomodoroSession session = PomodoroSession.builder()
                .title("Sessão de Foco")
                .startTime(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .isCompleted(false)
                .focusDuration(focusMinutes)
                .shortBreakDuration(shortBreakMinutes)
                .longBreakDuration(longBreakMinutes)
                .cycles(cycles)
                .user(user)
                .build();

        return pomodoroSessionRepository.save(session);
    }

    public PomodoroSession stopSession(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PomodoroSession session = pomodoroSessionRepository.findByUserAndIsActiveTrue(user)
                .orElseThrow(() -> new RuntimeException("Nenhuma sessão ativa encontrada."));

        session.setEndTime(LocalDateTime.now());
        session.setActive(false);
        session.setCompleted(true);

        return pomodoroSessionRepository.save(session);
    }

    public Optional<PomodoroSession> getActiveSession(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return pomodoroSessionRepository.findByUserAndIsActiveTrue(user);
    }

    public List<PomodoroSession> getSessionHistory(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return pomodoroSessionRepository.findByUser(user);
    }
}

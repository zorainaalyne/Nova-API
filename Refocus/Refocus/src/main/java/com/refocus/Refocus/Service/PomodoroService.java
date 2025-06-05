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
public class PomodoroService {

    private final PomodoroSessionRepository pomodoroSessionRepository;
    private final UserRepository userRepository;

    public PomodoroSession createSession(String userEmail, PomodoroSession session) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        session.setUser(user);
        session.setCreatedAt(LocalDateTime.now());
        session.setCompleted(false);

        return pomodoroSessionRepository.save(session);
    }

    public List<PomodoroSession> getSessions(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return pomodoroSessionRepository.findByUser(user);
    }

    public Optional<PomodoroSession> getSessionById(Long id) {
        return pomodoroSessionRepository.findById(id);
    }

    public PomodoroSession updateSession(Long id, PomodoroSession updatedSession) {
        return pomodoroSessionRepository.findById(id)
                .map(session -> {
                    session.setTitle(updatedSession.getTitle());
                    session.setFocusDuration(updatedSession.getFocusDuration());
                    session.setShortBreakDuration(updatedSession.getShortBreakDuration());
                    session.setLongBreakDuration(updatedSession.getLongBreakDuration());
                    session.setCycles(updatedSession.getCycles());
                    session.setCompleted(updatedSession.isCompleted());
                    return pomodoroSessionRepository.save(session);
                })
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));
    }

    public void deleteSession(Long id) {
        pomodoroSessionRepository.deleteById(id);
    }
}

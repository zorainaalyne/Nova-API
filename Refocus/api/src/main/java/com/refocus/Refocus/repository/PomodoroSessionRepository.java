package com.refocus.Refocus.repository;

import com.refocus.Refocus.model.PomodoroSession;
import com.refocus.Refocus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PomodoroSessionRepository extends JpaRepository<PomodoroSession, Long> {

    Optional<PomodoroSession> findByUserAndIsActiveTrue(User user);

    List<PomodoroSession> findByUser(User user);
}

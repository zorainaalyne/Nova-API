package com.refocus.Refocus.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pomodoro_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PomodoroSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;

    private boolean isActive;
    private boolean isCompleted;

    private int focusDuration;      // minutos de foco
    private int shortBreakDuration; // pausa curta em minutos
    private int longBreakDuration;  // pausa longa em minutos
    private int cycles;             // quantos ciclos

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

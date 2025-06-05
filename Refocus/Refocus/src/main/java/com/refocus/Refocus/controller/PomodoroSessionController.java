package com.refocus.Refocus.controller;

import com.refocus.Refocus.model.PomodoroSession;
import com.refocus.Refocus.Service.PomodoroSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pomodoro")
@RequiredArgsConstructor
public class PomodoroSessionController {

    private final PomodoroSessionService pomodoroSessionService;

    @PostMapping("/start")
    public ResponseEntity<PomodoroSession> startSession(
            @RequestParam int focusMinutes,
            @RequestParam int shortBreakMinutes,
            @RequestParam int longBreakMinutes,
            @RequestParam int cycles,
            Principal principal) {

        return ResponseEntity.ok(pomodoroSessionService
                .startSession(principal.getName(), focusMinutes, shortBreakMinutes, longBreakMinutes, cycles));
    }

    @PostMapping("/stop")
    public ResponseEntity<PomodoroSession> stopSession(Principal principal) {
        return ResponseEntity.ok(pomodoroSessionService.stopSession(principal.getName()));
    }

    @GetMapping("/active")
    public ResponseEntity<Optional<PomodoroSession>> getActiveSession(Principal principal) {
        return ResponseEntity.ok(pomodoroSessionService.getActiveSession(principal.getName()));
    }

    @GetMapping("/history")
    public ResponseEntity<List<PomodoroSession>> getSessionHistory(Principal principal) {
        return ResponseEntity.ok(pomodoroSessionService.getSessionHistory(principal.getName()));
    }
}

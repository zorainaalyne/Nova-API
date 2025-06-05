package com.refocus.Refocus.controller;

import com.refocus.Refocus.model.PomodoroSession;
import com.refocus.Refocus.Service.PomodoroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/pomodoro")
@RequiredArgsConstructor
public class PomodoroController {

    private final PomodoroService pomodoroService;

    @PostMapping
    public ResponseEntity<PomodoroSession> createSession(@RequestBody PomodoroSession session,
                                                         Principal principal) {
        return ResponseEntity.ok(pomodoroService.createSession(principal.getName(), session));
    }

    @GetMapping
    public ResponseEntity<List<PomodoroSession>> getSessions(Principal principal) {
        return ResponseEntity.ok(pomodoroService.getSessions(principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PomodoroSession> getSession(@PathVariable Long id) {
        return pomodoroService.getSessionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PomodoroSession> updateSession(@PathVariable Long id,
                                                         @RequestBody PomodoroSession session) {
        return ResponseEntity.ok(pomodoroService.updateSession(id, session));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        pomodoroService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}

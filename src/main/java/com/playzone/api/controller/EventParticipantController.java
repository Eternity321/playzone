package com.playzone.api.controller;

import com.playzone.api.dto.response.EventParticipantResponse;
import com.playzone.service.EventParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events/{eventId}/participants")
@RequiredArgsConstructor
public class EventParticipantController {

    private final EventParticipantService service;

    @Operation(summary = "Получить список участников события")
    @GetMapping
    public ResponseEntity<List<EventParticipantResponse>> getParticipants(@PathVariable Long eventId) {
        return ResponseEntity.ok(service.getParticipants(eventId));
    }

    @Operation(summary = "Присоединиться к событию (USER)")
    @Secured("ROLE_USER")
    @PostMapping("/join")
    public ResponseEntity<EventParticipantResponse> joinEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(service.joinEvent(eventId));
    }

    @Operation(summary = "Выйти из события (USER)")
    @Secured("ROLE_USER")
    @DeleteMapping("/leave")
    public ResponseEntity<Void> leaveEvent(@PathVariable Long eventId) {
        service.leaveEvent(eventId);
        return ResponseEntity.noContent().build();
    }
}

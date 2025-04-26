package com.playzone.api.controller;

import com.playzone.api.dto.mapper.EventMapper;
import com.playzone.api.dto.request.EventRequest;
import com.playzone.api.dto.response.EventResponse;
import com.playzone.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;
    private final EventMapper mapper;

    @Operation(summary = "Список всех событий")
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toResponse).toList());
    }

    @Operation(summary = "Событие по ID")
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(service.getById(id)));
    }

    @Operation(summary = "Создание события (USER)")
    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<EventResponse> create(@RequestBody EventRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.create(request)));
    }

    @Operation(summary = "Обновление события (USER)")
    @Secured("ROLE_USER")
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> update(@PathVariable Long id, @RequestBody EventRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.update(id, request)));
    }

    @Operation(summary = "Удаление события (ADMIN)")
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package com.playzone.api.controller;

import com.playzone.api.dto.mapper.SportTypeMapper;
import com.playzone.api.dto.request.SportTypeRequest;
import com.playzone.api.dto.response.SportTypeResponse;
import com.playzone.service.SportTypeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sport-types")
@RequiredArgsConstructor
public class SportTypeController {

    private final SportTypeService service;
    private final SportTypeMapper mapper;

    @Operation(summary = "Получить список всех типов спорта")
    @GetMapping
    public ResponseEntity<List<SportTypeResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toResponse).toList());
    }

    @Operation(summary = "Получить тип спорта по ID")
    @GetMapping("/{id}")
    public ResponseEntity<SportTypeResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(mapper.toResponse(service.getById(id)));
    }

    @Operation(summary = "Создать тип спорта (ADMIN)")
    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<SportTypeResponse> create(@RequestBody SportTypeRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.create(request)));
    }

    @Operation(summary = "Обновить тип спорта (ADMIN)")
    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<SportTypeResponse> update(@PathVariable Integer id, @RequestBody SportTypeRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.update(id, request)));
    }

    @Operation(summary = "Удалить тип спорта (ADMIN)")
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

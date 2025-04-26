package com.playzone.api.controller;

import com.playzone.api.dto.mapper.SportFacilityMapper;
import com.playzone.api.dto.request.SportFacilityRequest;
import com.playzone.api.dto.response.SportFacilityResponse;
import com.playzone.model.SportFacility;
import com.playzone.service.SportFacilityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/facilities")
@RequiredArgsConstructor
public class SportFacilityController {

    private final SportFacilityService service;
    private final SportFacilityMapper mapper;

    @Operation(summary = "Получение всех локаций")
    @GetMapping
    public ResponseEntity<List<SportFacilityResponse>> getAll() {
        List<SportFacilityResponse> response = service.getAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Получение локации по ID")
    @GetMapping("/{id}")
    public ResponseEntity<SportFacilityResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(service.getById(id)));
    }

    @Operation(summary = "Создание локации (USER)")
    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<SportFacilityResponse> create(@RequestBody SportFacilityRequest request) {
        SportFacility facility = service.create(request);
        return ResponseEntity.ok(mapper.toResponse(facility));
    }

    @Operation(summary = "Обновление локации (USER)")
    @Secured("ROLE_USER")
    @PutMapping("/{id}")
    public ResponseEntity<SportFacilityResponse> update(@PathVariable Long id, @RequestBody SportFacilityRequest request) {
        SportFacility updated = service.update(id, request);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @Operation(summary = "Удаление локации (ADMIN)")
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

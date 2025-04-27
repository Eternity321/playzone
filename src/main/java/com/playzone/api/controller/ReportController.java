package com.playzone.api.controller;

import com.playzone.api.dto.mapper.ReportMapper;
import com.playzone.api.dto.request.ReportRequest;
import com.playzone.api.dto.response.ReportResponse;
import com.playzone.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService service;
    private final ReportMapper mapper;

    @Operation(summary = "Список всех репортов")
    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<List<ReportResponse>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toResponse).toList());
    }

    @Operation(summary = "Создать репорт (USER)")
    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<ReportResponse> create(@RequestBody ReportRequest request) {
        return ResponseEntity.ok(mapper.toResponse(service.create(request)));
    }

    @Operation(summary = "Удалить репорт (ADMIN)")
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

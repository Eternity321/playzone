package com.playzone.api.controller;

import com.playzone.api.dto.mapper.SportFacilityMapper;
import com.playzone.api.dto.request.SportFacilityRequest;
import com.playzone.api.dto.response.SportFacilityResponse;
import com.playzone.model.SportFacility;
import com.playzone.service.SportFacilityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/facilities")
@RequiredArgsConstructor
public class SportFacilityController {

    private final SportFacilityService sportFacilityService;
    private final SportFacilityMapper mapper;

    @Operation(summary = "Получение всех локаций")
    @GetMapping
    public ResponseEntity<List<SportFacilityResponse>> getAll() {
        List<SportFacilityResponse> response = sportFacilityService.getAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Получение локации по ID")
    @GetMapping("/{id}")
    public ResponseEntity<SportFacilityResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(sportFacilityService.getById(id)));
    }

    @Operation(summary = "Создание локации (USER)")
    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<SportFacilityResponse> create(@RequestBody SportFacilityRequest request) {
        SportFacility facility = sportFacilityService.create(request);
        return ResponseEntity.ok(mapper.toResponse(facility));
    }

    @Operation(summary = "Обновление локации (USER)")
    @Secured("ROLE_USER")
    @PutMapping("/{id}")
    public ResponseEntity<SportFacilityResponse> update(@PathVariable Long id, @RequestBody SportFacilityRequest request) {
        SportFacility updated = sportFacilityService.update(id, request);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @Operation(summary = "Удаление локации (ADMIN)")
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sportFacilityService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Добавить несколько фото к спортивному объекту (USER)")
    @Secured("ROLE_USER")
    @PostMapping(value = "/{facilityId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> uploadFacilityPhotos(@PathVariable Long facilityId,
                                                             @RequestParam("files") List<MultipartFile> files) {
        List<String> photoUrls = sportFacilityService.uploadFacilityPhotos(facilityId, files);
        return ResponseEntity.ok(photoUrls);
    }

    @Operation(summary = "Удалить фото спортивного объекта (USER)")
    @Secured("ROLE_USER")
    @DeleteMapping("/{facilityId}/photos/{photoId}")
    public ResponseEntity<Void> deleteFacilityPhoto(@PathVariable Long facilityId, @PathVariable Long photoId) {
        sportFacilityService.deleteFacilityPhoto(facilityId, photoId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Удалить фото спортивного объекта по пути (USER)")
    @Secured("ROLE_USER")
    @DeleteMapping("/{facilityId}/photos/by-path")
    public ResponseEntity<Void> deleteFacilityPhotoByPath(
            @PathVariable Long facilityId,
            @RequestParam String path) {
        sportFacilityService.deleteFacilityPhotoByPath(facilityId, path);
        return ResponseEntity.noContent().build();
    }
}

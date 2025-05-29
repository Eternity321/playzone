package com.playzone.api.controller;

import com.playzone.api.dto.mapper.FacilityCommentMapper;
import com.playzone.api.dto.request.FacilityCommentRequest;
import com.playzone.api.dto.response.FacilityCommentResponse;
import com.playzone.model.FacilityComment;
import com.playzone.service.FacilityCommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/facilities/{facilityId}/comments")
@RequiredArgsConstructor
public class FacilityCommentController {

    private final FacilityCommentService commentService;
    private final FacilityCommentMapper mapper;

    @Operation(summary = "Создание комментария к локации (USER)")
    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<FacilityCommentResponse> create(@PathVariable Long facilityId,
                                                          @RequestBody FacilityCommentRequest request) {
        FacilityComment comment = commentService.create(facilityId, request);
        return ResponseEntity.ok(mapper.toResponse(comment));
    }

    @Operation(summary = "Получение всех комментариев к локации")
    @GetMapping
    public ResponseEntity<List<FacilityCommentResponse>> getAll(@PathVariable Long facilityId) {
        List<FacilityCommentResponse> responses = commentService.getAllByFacility(facilityId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Удаление комментария (USER)")
    @Secured("ROLE_USER")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Редактирование комментария (USER)")
    @Secured("ROLE_USER")
    @PutMapping("/{commentId}")
    public ResponseEntity<FacilityCommentResponse> update(@PathVariable Long facilityId,
                                                          @PathVariable Long commentId,
                                                          @RequestBody FacilityCommentRequest request) {
        FacilityComment updatedComment = commentService.update(commentId, request);
        return ResponseEntity.ok(mapper.toResponse(updatedComment));
    }

    @Operation(summary = "Получить среднюю оценку локации")
    @GetMapping("/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long facilityId) {
        return ResponseEntity.ok(commentService.getAverageRating(facilityId));
    }
}
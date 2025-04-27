package com.playzone.service;

import com.playzone.api.dto.mapper.ReportMapper;
import com.playzone.api.dto.request.ReportRequest;
import com.playzone.model.Report;
import com.playzone.repository.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper mapper;
    private final UserService userService;
    private final SportFacilityService sportFacilityService;
    private final FacilityCommentService facilityCommentService;
    private final EventService eventService;

    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    @Transactional
    public Report create(ReportRequest request) {
        validateTargetExists(request.getTargetType(), request.getTargetId());
        Report report = mapper.toEntity(request);
        return reportRepository.save(report);
    }

    private void validateTargetExists(String targetType, Long targetId) {
        switch (Report.TargetType.valueOf(targetType)) {
            case USER -> {
                if (!userService.existsById(targetId)) {
                    throw new IllegalArgumentException("Пользователь с id " + targetId + " не найден");
                }
            }
            case FACILITY -> {
                if (!sportFacilityService.existsById(targetId)) {
                    throw new IllegalArgumentException("Объект с id " + targetId + " не найден");
                }
            }
            case COMMENT -> {
                if (!facilityCommentService.existsById(targetId)) {
                    throw new IllegalArgumentException("Комментарий с id " + targetId + " не найден");
                }
            }
            case EVENT -> {
                if (!eventService.existsById(targetId)) {
                    throw new IllegalArgumentException("Событие с id " + targetId + " не найдено");
                }
            }
            default -> throw new IllegalArgumentException("Неизвестный тип цели");
        }
    }

    @Transactional
    public void delete(Long id) {
        reportRepository.deleteById(id);
    }
}

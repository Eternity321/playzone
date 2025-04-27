package com.playzone.api.dto.mapper;

import com.playzone.api.dto.request.ReportRequest;
import com.playzone.api.dto.response.ReportResponse;
import com.playzone.model.Report;
import com.playzone.model.User;
import com.playzone.service.AuthService;
import com.playzone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportMapper {

    private final AuthService authService;
    private final UserService userService;

    public Report toEntity(ReportRequest request) {
        Report report = new Report();
        String username = authService.getCurrentUsername();
        User reporter = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        report.setReporter(reporter);
        report.setTargetType(Report.TargetType.valueOf(request.getTargetType()));
        report.setTargetId(request.getTargetId());
        report.setReason(request.getReason());
        return report;
    }

    public ReportResponse toResponse(Report report) {
        ReportResponse response = new ReportResponse();
        response.setId(report.getId());
        response.setReporterNickname(report.getReporter().getNickname());
        response.setTargetType(report.getTargetType());
        response.setTargetId(report.getTargetId());
        response.setDescription(report.getReason());
        return response;
    }
}

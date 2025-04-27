package com.playzone.api.dto.response;

import com.playzone.model.Report;
import lombok.Data;

@Data
public class ReportResponse {
    private Long id;
    private String reporterNickname;
    private Report.TargetType targetType;
    private Long targetId;
    private String description;
}

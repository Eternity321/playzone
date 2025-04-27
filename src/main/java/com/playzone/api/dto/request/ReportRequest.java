package com.playzone.api.dto.request;

import lombok.Data;

@Data
public class ReportRequest {
    private String targetType;
    private Long targetId;
    private String reason;
}

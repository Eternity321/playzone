package com.playzone.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.playzone.model.Event;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventResponse {
    private Long id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(example = "13:00")
    private LocalTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(example = "14:00")
    private LocalTime endTime;
    private LocalDate eventDate;
    private String sportTypeName;
    private String createdBy;
    private Long facilityId;
    private Event.EventStatus status;
}

package com.playzone.api.dto.request;

import lombok.Data;

@Data
public class EventParticipantRequest {
    private Long eventId;
    private Long userId;
}

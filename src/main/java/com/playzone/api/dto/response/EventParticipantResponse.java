package com.playzone.api.dto.response;

import lombok.Data;

@Data
public class EventParticipantResponse {
    private Long id;
    private Long eventId;
    private Long userId;
    private String userNickname;
    private String avatarKey;
}

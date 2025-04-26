package com.playzone.api.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FacilityCommentResponse {
    private Long id;
    private Integer rating;
    private String text;
    private Long userId;
    private String userNickname;
    private String userAvatarKey;
    private LocalDateTime createdAt;
}

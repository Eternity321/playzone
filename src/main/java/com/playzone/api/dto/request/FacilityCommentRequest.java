package com.playzone.api.dto.request;

import lombok.Data;

@Data
public class FacilityCommentRequest {
    private Integer rating;
    private String text;
}

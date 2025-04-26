package com.playzone.api.dto.request;

import lombok.Data;

@Data
public class SportFacilityRequest {
    private String name;
    private String address;
    private Long sportTypeId;
    private Double latitude;
    private Double longitude;
    private String description;
}

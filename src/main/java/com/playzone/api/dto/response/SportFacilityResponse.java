package com.playzone.api.dto.response;

import lombok.Data;

@Data
public class SportFacilityResponse {
    private Integer sport_facility_id;
    private String name;
    private String address;
    private String sportTypeName;
    private Double latitude;
    private Double longitude;
    private String description;
    private String createdByNickname;
}

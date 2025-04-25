package com.playzone.api.dto.mapper;

import com.playzone.api.dto.request.SportTypeRequest;
import com.playzone.api.dto.response.SportTypeResponse;
import com.playzone.model.SportType;
import org.springframework.stereotype.Component;

@Component
public class SportTypeMapper {

    public SportType toEntity(SportTypeRequest request) {
        SportType sportType = new SportType();
        sportType.setName(request.getName());
        return sportType;
    }

    public SportTypeResponse toResponse(SportType sportType) {
        SportTypeResponse response = new SportTypeResponse();
        response.setSport_type_id(sportType.getSport_type_id());
        response.setName(sportType.getName());
        return response;
    }
}

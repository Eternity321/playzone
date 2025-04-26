package com.playzone.api.dto.mapper;

import com.playzone.api.dto.request.SportFacilityRequest;
import com.playzone.api.dto.response.SportFacilityResponse;
import com.playzone.model.SportFacility;
import com.playzone.repository.SportTypeRepository;
import com.playzone.service.FacilityCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SportFacilityMapper {

    @Autowired
    private SportTypeRepository sportTypeRepository;
    @Autowired
    private FacilityCommentService facilityCommentService;

    public SportFacility toEntity(SportFacilityRequest request) {
        SportFacility facility = new SportFacility();
        facility.setName(request.getName());
        facility.setAddress(request.getAddress());
        facility.setLatitude(request.getLatitude());
        facility.setLongitude(request.getLongitude());
        facility.setDescription(request.getDescription());
        facility.setSportType(
                sportTypeRepository.findById(request.getSportTypeId())
                        .orElseThrow(() -> new IllegalArgumentException("Вид спорта не найден")));
        return facility;
    }

    public SportFacilityResponse toResponse(SportFacility facility) {
        SportFacilityResponse response = new SportFacilityResponse();
        response.setId(facility.getId());
        response.setName(facility.getName());
        response.setAddress(facility.getAddress());
        response.setLatitude(facility.getLatitude());
        response.setLongitude(facility.getLongitude());
        response.setDescription(facility.getDescription());
        response.setCreatedByNickname(facility.getCreatedBy().getNickname());
        response.setSportTypeName(facility.getSportType().getName());
        Double avgRating = facilityCommentService.getAverageRating(facility.getId());
        response.setAverageRating(avgRating);
        return response;
    }

}

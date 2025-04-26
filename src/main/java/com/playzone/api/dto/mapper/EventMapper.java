package com.playzone.api.dto.mapper;

import com.playzone.api.dto.request.EventRequest;
import com.playzone.api.dto.response.EventResponse;
import com.playzone.model.Event;
import com.playzone.model.User;
import com.playzone.service.AuthService;
import com.playzone.service.SportFacilityService;
import com.playzone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final SportFacilityService facilityService;
    private final UserService userService;
    private final AuthService authService;

    public Event toEntity(EventRequest request) {
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setEventDate(request.getEventDate());
        var facility = facilityService.getById(request.getFacilityId());
        event.setFacility(facility);
        event.setSportType(facility.getSportType());
        String username = authService.getCurrentUsername();
        User creator = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        event.setCreator(creator);

        return event;
    }

    public EventResponse toResponse(Event event) {
        EventResponse response = new EventResponse();
        response.setId(event.getId());
        response.setTitle(event.getTitle());
        response.setStartTime(event.getStartTime());
        response.setEndTime(event.getEndTime());
        response.setEventDate(event.getEventDate());
        response.setSportTypeName(event.getSportType().getName());
        response.setCreatedBy(event.getCreator().getNickname());
        response.setFacilityId(event.getFacility().getId());
        response.setStatus(event.getStatus());
        return response;
    }
}

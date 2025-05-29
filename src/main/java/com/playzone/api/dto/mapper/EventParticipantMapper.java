package com.playzone.api.dto.mapper;

import com.playzone.api.dto.request.EventParticipantRequest;
import com.playzone.api.dto.response.EventParticipantResponse;
import com.playzone.model.Event;
import com.playzone.model.EventParticipant;
import com.playzone.model.User;
import org.springframework.stereotype.Component;

@Component
public class EventParticipantMapper {

    public EventParticipant toEntity(EventParticipantRequest request, Event event, User user) {
        EventParticipant participant = new EventParticipant();
        participant.setEvent(event);
        participant.setUser(user);
        return participant;
    }

    public EventParticipantResponse toResponse(EventParticipant participant) {
        EventParticipantResponse response = new EventParticipantResponse();
        response.setId(participant.getId());
        response.setEventId(participant.getEvent().getId());
        response.setUserId(participant.getUser().getId());
        response.setUserNickname(participant.getUser().getNickname());
        response.setAvatarKey(participant.getUser().getUserAvatarKey());
        return response;
    }
}

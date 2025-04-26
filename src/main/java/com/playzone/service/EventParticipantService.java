package com.playzone.service;

import com.playzone.api.dto.mapper.EventParticipantMapper;
import com.playzone.api.dto.response.EventParticipantResponse;
import com.playzone.model.Event;
import com.playzone.model.EventParticipant;
import com.playzone.model.User;
import com.playzone.repository.EventParticipantRepository;
import com.playzone.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventParticipantService {

    private final EventParticipantRepository repository;
    private final EventRepository eventRepository;
    private final UserService userService;
    private final AuthService authService;
    private final EventParticipantMapper mapper;

    public List<EventParticipantResponse> getParticipants(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Событие не найдено"));

        return repository.findByEvent(event)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public EventParticipantResponse joinEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Событие не найдено"));
        String username = authService.getCurrentUsername();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        repository.findByEventAndUser(event, user)
                .ifPresent(ep -> {
                    throw new IllegalArgumentException("Пользователь уже присоединился к событию");
                });

        EventParticipant participant = new EventParticipant();
        participant.setEvent(event);
        participant.setUser(user);
        participant = repository.save(participant);

        return mapper.toResponse(participant);
    }

    @Transactional
    public void addCreatorAsParticipant(Event event) {
        User creator = event.getCreator();
        if (creator == null) {
            throw new IllegalArgumentException("У события отсутствует создатель");
        }

        boolean alreadyParticipant = repository.findByEventAndUser(event, creator).isPresent();
        if (!alreadyParticipant) {
            EventParticipant participant = new EventParticipant();
            participant.setEvent(event);
            participant.setUser(creator);
            repository.save(participant);
        }
    }

    @Transactional
    public void leaveEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Событие не найдено"));
        String username = authService.getCurrentUsername();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        EventParticipant participant = repository.findByEventAndUser(event, user)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не присоединен к этому событию"));

        repository.delete(participant);
    }
}

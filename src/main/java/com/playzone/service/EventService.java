package com.playzone.service;

import com.playzone.api.dto.mapper.EventMapper;
import com.playzone.api.dto.request.EventRequest;
import com.playzone.model.Event;
import com.playzone.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;
    private final EventMapper mapper;
    private final EventParticipantService eventParticipantService;
    private final EventRepository eventRepository;

    public List<Event> getAll() {
        return repository.findAll();
    }

    public Event getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Событие не найдено: " + id));
    }

    @Transactional
    public Event create(EventRequest request) {
        LocalTime now = LocalTime.now(ZoneId.of("Europe/Moscow"));
        if (request.getStartTime().isBefore(now)) {
            throw new IllegalArgumentException("Невозможно создать событие, так как его время начала уже прошло.");
        }
        if (request.getEndTime().isBefore(now) || request.getEndTime().isBefore(request.getStartTime())) {
            throw new IllegalArgumentException("Невозможно создать событие, так как его время завершения уже прошло или оно раньше начала.");
        }

        Event event = mapper.toEntity(request);
        event.setStatus(Event.EventStatus.PLANNED);
        event = repository.save(event);

        eventParticipantService.addCreatorAsParticipant(event);

        return event;
    }


    @Transactional
    public Event update(Long id, EventRequest request) {
        Event existing = getById(id);
        Event updated = mapper.toEntity(request);
        updated.setId(existing.getId());
        updated.setCreator(existing.getCreator());
        updateEventStatus(updated);
        return repository.save(updated);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void updateEventStatus(Event event) {
        LocalTime now = LocalTime.now(ZoneId.of("Europe/Moscow"));
        ZonedDateTime eventStartTime = event.getStartTime().atDate(event.getEventDate()).atZone(ZoneId.of("Europe/Moscow"));
        ZonedDateTime eventEndTime = event.getEndTime().atDate(event.getEventDate()).atZone(ZoneId.of("Europe/Moscow"));
        ZonedDateTime nowZoned = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        if (eventStartTime.isAfter(nowZoned)) {
            if (event.getStatus() != Event.EventStatus.PLANNED) {
                event.setStatus(Event.EventStatus.PLANNED);
            }
        } else if (eventEndTime.isBefore(nowZoned)) {
            if (event.getStatus() != Event.EventStatus.COMPLETED) {
                event.setStatus(Event.EventStatus.COMPLETED);
            }
        } else {
            if (event.getStatus() != Event.EventStatus.ACTIVE) {
                event.setStatus(Event.EventStatus.ACTIVE);
            }
        }
    }


    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void updateEventStatuses() {
        LocalTime now = LocalTime.now(ZoneId.of("Europe/Moscow"));
        System.out.println("Обновление статусов событий: " + now);
        List<Event> events = repository.findByStatusNot(Event.EventStatus.COMPLETED);

        for (Event event : events) {
            Event.EventStatus oldStatus = event.getStatus();
            updateEventStatus(event);
            if (oldStatus != event.getStatus()) {
                repository.save(event);
                System.out.println("Обновлен статус события: " + event.getId() + " на " + event.getStatus());
            }
        }
    }

    public boolean existsById(Long id) {
        return eventRepository.existsById(id);
    }
}

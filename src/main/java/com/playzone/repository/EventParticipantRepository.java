package com.playzone.repository;

import com.playzone.model.Event;
import com.playzone.model.EventParticipant;
import com.playzone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {
    List<EventParticipant> findByEvent(Event event);

    Optional<EventParticipant> findByEventAndUser(Event event, User user);
}

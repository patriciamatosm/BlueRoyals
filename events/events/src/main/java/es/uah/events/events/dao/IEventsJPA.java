package es.uah.events.events.dao;

import es.uah.events.events.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEventsJPA extends JpaRepository<Events, Integer> {
    List<Events> findByEventName(String eventName);
}

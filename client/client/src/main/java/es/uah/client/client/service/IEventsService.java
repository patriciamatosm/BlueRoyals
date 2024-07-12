package es.uah.client.client.service;

import es.uah.client.client.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEventsService {
    Page<Event> findAll(Pageable pageable);

    List<Event> findAll();

    List<Event> findEventsByEventName(String eventName);

    Event findEventsById(Integer id);

    void saveEvents(Event event);

    void deleteEvents(Integer id);
}

package es.uah.events.events.service;

import es.uah.events.events.model.Events;

import java.util.List;

public interface IEventsService {
    List<Events> findAll();

    List<Events> findEventsByEventName(String eventName);

    Events findEventsById(Integer id);

    void saveEvents(Events event);

    void deleteEvents(Integer id);

    List<Events> findEventsByIdUser(Integer id);

    List<Events> findNearbyEvents(Double longitude, Double latitude, Double maxDistance);
}

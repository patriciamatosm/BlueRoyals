package es.uah.events.events.dao;

import es.uah.events.events.model.Events;

import java.util.List;

public interface IEventsDAO {

    List<Events> findAll();

    List<Events> findEventsByEventName(String eventName);

    Events findEventsById(Integer id);

    void saveEvents(Events event);

    void deleteEvents(Integer id);

    List<Events> findEventsByCreateUser(Integer createUser);
}

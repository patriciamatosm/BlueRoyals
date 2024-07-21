package es.uah.events.events.service;

import es.uah.events.events.dao.IEventsDAO;
import es.uah.events.events.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsServiceImpl implements IEventsService{
    @Autowired
    IEventsDAO eventsDAO;


    @Override
    public List<Events> findAll() {
        return eventsDAO.findAll();
    }

    @Override
    public List<Events> findEventsByEventName(String eventName) {
        return eventsDAO.findEventsByEventName(eventName);
    }

    @Override
    public Events findEventsById(Integer id) {
        return eventsDAO.findEventsById(id);
    }

    @Override
    public List<Events> findEventsByIdUser(Integer id) {
       return eventsDAO.findEventsByCreateUser(id);
   }

    @Override
    public void saveEvents(Events event) {
        eventsDAO.saveEvents(event);
    }

    @Override
    public void deleteEvents(Integer id) {
        eventsDAO.deleteEvents(id);
    }
}

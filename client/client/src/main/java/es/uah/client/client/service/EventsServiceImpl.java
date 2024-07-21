package es.uah.client.client.service;

import es.uah.client.client.model.Event;
import es.uah.client.client.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class EventsServiceImpl implements IEventsService{
    @Autowired
    RestTemplate template;

    String url = "http://localhost:8091/api/events/events";

    @Override
    public Page<Event> findAll(Pageable pageable) {
        Event[] Events = template.getForObject(url, Event[].class);
        List<Event> EventsList = Arrays.asList(Events);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Event> list;

        if (EventsList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, EventsList.size());
            list = EventsList.subList(startItem, toIndex);
        }

        Page<Event> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), EventsList.size());
        return page;
    }

    @Override
    public List<Event> findAll() {
        Event[] events = template.getForObject(url, Event[].class);
        return Arrays.asList(events);
    }

    @Override
    public List<Event> findEventsByEventName(String eventName) {
        Event[] events = template.getForObject(url + "/find/" + eventName, Event[].class);
        return Arrays.asList(events);
    }

    @Override
    public List<Event> findEventsByCreateUser(Integer id) {
        Event[] events = template.getForObject(url + "/idUser/" + id, Event[].class);
        return Arrays.asList(events);
    }

    @Override
    public Event findEventsById(Integer id) {
        return template.getForObject(url + '/' + id, Event.class);
    }

    @Override
    public void saveEvents(Event event) {
        if (event.getId() != null && event.getId() > 0) {
            template.postForObject(url, event,  String.class);
        } else {
            event.setId(0);
            template.postForObject(url, event, String.class);
        }
    }

    @Override
    public void deleteEvents(Integer id) {
        template.delete(url + "/" + id);
    }
}

package es.uah.client.client.controller;

import es.uah.client.client.model.Event;
import es.uah.client.client.model.Subscription;
import es.uah.client.client.model.User;
import es.uah.client.client.service.IEventsService;
import es.uah.client.client.service.ISubscriptionsService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventsController {

    @Autowired
    IEventsService eventsService;

    @Autowired
    ISubscriptionsService subscriptionsService;

    public List<Event> findAll() {
        return eventsService.findAll();
    }

    @ResponseBody
    public List<Event> findEventsByUserCreated(@RequestBody User u) {
        return eventsService.findEventsByCreateUser(u.getId());
    }

    @ResponseBody
    public List<Event> findEventsByUser(@RequestBody User u) {
        List<Subscription> s = subscriptionsService.findUserSubscriptions(u.getId());
        List<Event> events = new ArrayList<>();
        for(Subscription sub : s){
            Event e = eventsService.findEventsById(sub.getIdEvent());
            if(e != null) events.add(e);
        }
        return events;
    }

    @ResponseBody
    public Event save(@RequestBody Event r) {
        r.setDelete(false);
        eventsService.saveEvents(r);
        return (Event) eventsService.findEventsByEventName(r.getEventName());
    }

    @ResponseBody
    public void delete(@RequestBody Integer event) {
        eventsService.deleteEvents(event);
    }


}

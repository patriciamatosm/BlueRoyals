package es.uah.client.client.controller;

import es.uah.client.client.model.Event;
import es.uah.client.client.model.Subscription;
import es.uah.client.client.model.User;
import es.uah.client.client.service.IEventsService;
import es.uah.client.client.service.ISubscriptionsService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventsController {

    @Autowired
    IEventsService eventsService;

    @Autowired
    ISubscriptionsService subscriptionsService;

    @GetMapping("/listAll")
    public List<Event> findAll() {
        return eventsService.findAll();
    }

    @GetMapping("/list/")
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

    @PostMapping("/save/")
    @ResponseBody
    public Event save(@RequestBody  Event r) {
        eventsService.saveEvents(r);
        return (Event) eventsService.findEventsByEventName(r.getEventName());
    }


}

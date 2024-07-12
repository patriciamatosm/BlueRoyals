package es.uah.events.events.controller;

import es.uah.events.events.model.Events;
import es.uah.events.events.service.IEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventsController {
    @Autowired
    IEventsService eventsService;


    @GetMapping("/events")
    public List<Events> findAll(){
        return eventsService.findAll();
    }

    @GetMapping("/events/{id}")
    public Events findEventsById(@PathVariable("id") Integer id) {
        return eventsService.findEventsById(id);
    }


    @GetMapping("/events/find/{username}")
    public List<Events> findEventsByEventName(@PathVariable("username") String username) {
        return eventsService.findEventsByEventName(username);
    }

    @PostMapping("/events")
    public void saveEvents(@RequestBody Events user) {
        eventsService.saveEvents(user);
    }

    @DeleteMapping("/events/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        eventsService.deleteEvents(id);
    }

}

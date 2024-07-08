package es.uah.events.events.controller;

import es.uah.events.events.model.Events;
import es.uah.events.events.service.IEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventsController {
    @Autowired
    IEventsService eventsService;


    @GetMapping("/events")
    public List<Events> findAll(){
        return eventsService.findAll();
    }

}

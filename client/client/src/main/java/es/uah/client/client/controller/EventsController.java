package es.uah.client.client.controller;

import es.uah.client.client.model.Event;
import es.uah.client.client.paginator.PageRender;
import es.uah.client.client.service.IEventsService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventsController {

    @Autowired
    IEventsService eventsService;


    @GetMapping("/listAll")
    public List<Event> findAll() {
        return eventsService.findAll();
    }

    @PostMapping("/save/")
    @ResponseBody
    public Event save(@RequestBody  Event r) {
        eventsService.saveEvents(r);
        return (Event) eventsService.findEventsByEventName(r.getEventName());
    }


}

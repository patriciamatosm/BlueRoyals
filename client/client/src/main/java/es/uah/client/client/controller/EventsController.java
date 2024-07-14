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

    @GetMapping(value = {"/", "/home", ""})
    public String home(Model model) {
        Event event = new Event();
        model.addAttribute("event", event);
        return "login";
    }

    @GetMapping(value = {"/index"})
    public String home2(Model model) {
        Event movie = new Event();
        model.addAttribute("Event", movie);
        return "home";
    }
    @GetMapping("/list")
    public String list(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Event> listado = eventsService.findAll(pageable);
        PageRender<Event> pageRender = new PageRender<Event>("/events/list", listado);
        model.addAttribute("title", "Eventos");
        model.addAttribute("listEvent", listado);
        model.addAttribute("page", pageRender);
        return "Event/listEvent";
    }

    @GetMapping("/listAll")
    public List<Event> findAll() {
        return eventsService.findAll();
    }

    @GetMapping("/new")
    public String nuevo(Model model) {
        model.addAttribute("title", "Nuevo evento");
        Event event = new Event();
        model.addAttribute("Event", event);
        return "register";
    }

    @PostMapping("/save/")
    public String save(Model model, Event r, RedirectAttributes attributes) {
        eventsService.saveEvents(r);
        model.addAttribute("title", "Nuevo evento");
        attributes.addFlashAttribute("msg", "El evento ha sido creado correctamente!");
        return "redirect:/events/list";
    }

    @GetMapping("/update/{id}")
    public String updateEvent(Model model, @PathVariable("id") Integer id) {
        Event Event = eventsService.findEventsById(id);
        model.addAttribute("title", "Editar evento");
        model.addAttribute("Event", Event);
        return "register";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        eventsService.deleteEvents(id);
        attributes.addFlashAttribute("msg", "El evento ha sido eliminado!");
        return "redirect:/events/list";
    }
}

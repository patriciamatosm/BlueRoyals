package es.uah.client.client.controller;

import es.uah.client.client.model.Event;
import es.uah.client.client.model.Subscription;
import es.uah.client.client.paginator.PageRender;
import es.uah.client.client.service.ISubscriptionsService;
import es.uah.client.client.service.ISubscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/subs")
public class SubscriptionsController {
    @Autowired
    ISubscriptionsService subsService;

    @GetMapping(value = {"/", "/home", ""})
    public String home(Model model) {
        Subscription Subscription = new Subscription();
        model.addAttribute("Subscription", Subscription);
        return "login";
    }

    @GetMapping(value = {"/index"})
    public String home2(Model model) {
        Subscription movie = new Subscription();
        model.addAttribute("Subscription", movie);
        return "home";
    }

    @GetMapping("/new")
    public String nuevo(Model model) {
        model.addAttribute("title", "Nuevo Subscriptiono");
        Subscription Subscription = new Subscription();
        model.addAttribute("Subscription", Subscription);
        return "register";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Subscription> listado = subsService.findAll(pageable);
        PageRender<Subscription> pageRender = new PageRender<Subscription>("/events/list", listado);
        model.addAttribute("title", "Eventos");
        model.addAttribute("listEvent", listado);
        model.addAttribute("page", pageRender);
        return "Event/listEvent";
    }

    @PostMapping("/save/")
    public String save(Model model, Subscription r, RedirectAttributes attributes) {
        subsService.saveSubscription(r);
        model.addAttribute("title", "Suscrito correctamente al evento!");
        return "redirect:/subs/list";
    }

    @GetMapping("/update/{id}")
    public String updateSubscription(Model model, @PathVariable("id") Integer id) {
        Subscription Subscription = subsService.findSubscriptionsById(id);
        model.addAttribute("title", "Editar");
        model.addAttribute("Subscription", Subscription);
        return "register";
    }

    @GetMapping("/delete/{id}")
    public String deleteSubscription(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        subsService.deleteSubscription(id);
        attributes.addFlashAttribute("msg", "Desuscrito!");
        return "redirect:/subs/list";
    }
}

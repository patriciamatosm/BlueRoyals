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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/subs")
public class SubscriptionsController {
    @Autowired
    ISubscriptionsService subsService;

    @ResponseBody
    public List<Subscription> findByIdEvent(@RequestBody Integer id) {
        return subsService.findSubscriptionsByEvent(id);
    }

    @ResponseBody
    public Subscription findByIdEventIdUser(@RequestBody Integer idEvent, @RequestBody Integer idUser) {
        return subsService.findSubscriptionsByEventAndUser(idEvent, idUser);
    }

    @ResponseBody
    public Boolean subscribe(@RequestBody Subscription s) {
        s.setIsDelete(false);
        subsService.saveSubscription(s);
        return true;
    }

    @ResponseBody
    public Boolean unsubscribe(@RequestBody Subscription s) throws Exception{
        System.out.println("Unsubscribing: " + s);
        subsService.deleteSubscription(s.getId());
        return true;
    }
}

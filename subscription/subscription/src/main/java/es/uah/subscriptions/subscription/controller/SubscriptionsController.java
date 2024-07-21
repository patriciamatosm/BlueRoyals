package es.uah.subscriptions.subscription.controller;

import es.uah.subscriptions.subscription.model.Subscriptions;
import es.uah.subscriptions.subscription.service.ISubscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubscriptionsController {
    @Autowired
    ISubscriptionsService subscriptionsService;

    @GetMapping("/subs")
    public List<Subscriptions> findAll(){
        return subscriptionsService.findAll();
    }

    @GetMapping("/subs/event/{idEvent}")
    public List<Subscriptions> findSubsByIdEvent(@PathVariable("idEvent") Integer id) {
        return subscriptionsService.findSubscriptionsByEvent(id);
    }

    @GetMapping("/subs/{idEvent}/{idUser}")
    public Subscriptions findSubsByIdEventIdUser(@PathVariable("idEvent") Integer idEvent,
                                                       @PathVariable("idUser") Integer idUser) {
        return subscriptionsService.findByIdUserAndIdEvent(idEvent, idUser);
    }

    @GetMapping("/subs/user/{idUser}")
    public List<Subscriptions> findSubsByIdUser(@PathVariable("idUser") Integer id) {
        return subscriptionsService.findUserSubscriptions(id);
    }

    @GetMapping("/subs/{id}")
    public Subscriptions findSubscriptionById(@PathVariable("id") Integer id) {
        return subscriptionsService.findSubscriptionById(id);
    }

    @PostMapping("/subs")
    public void saveUser(@RequestBody Subscriptions sub) {
        sub.setDelete(false);
        subscriptionsService.saveSubscription(sub);
    }

    @PutMapping("/subs")
    public void updateActor(@RequestBody Subscriptions sub) {
        subscriptionsService.updateSubscription(sub);
    }

    @DeleteMapping("/subs/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        subscriptionsService.deleteSubscription(id);
    }

}

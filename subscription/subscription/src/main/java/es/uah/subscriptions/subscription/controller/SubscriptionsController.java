package es.uah.subscriptions.subscription.controller;

import es.uah.subscriptions.subscription.model.Subscriptions;
import es.uah.subscriptions.subscription.service.ISubscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubscriptionsController {
    @Autowired
    ISubscriptionsService subscriptionsService;

    @GetMapping("/subscriptions")
    public List<Subscriptions> findAll(){
        return subscriptionsService.findAll();
    }
}

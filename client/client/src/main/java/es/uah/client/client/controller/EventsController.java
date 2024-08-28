package es.uah.client.client.controller;

import es.uah.client.client.model.Event;
import es.uah.client.client.model.Subscription;
import es.uah.client.client.model.User;
import es.uah.client.client.service.IEventsService;
import es.uah.client.client.service.ISubscriptionsService;
import es.uah.client.client.service.IUploadImageService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventsController {

    @Autowired
    IEventsService eventsService;

    @Autowired
    IUploadImageService uploadImageService;

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
    public Boolean saveEvent(@RequestBody Event r) {
        try{
            eventsService.saveEvents(r);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @ResponseBody
    public String uploadEventImage(@RequestBody String fileName, @RequestBody InputStream input) {
        try{
            return uploadImageService.saveImage(input, fileName);
        } catch (Exception e) {
            return null;
        }
    }

    @ResponseBody
    public void delete(@RequestBody Integer event) {
        List<Subscription> s = subscriptionsService.findSubscriptionsByEvent(event);
        for(Subscription a : s){
            System.out.println("Unsubscribing from: " + a);
            subscriptionsService.deleteSubscription(a.getId());
        }
        eventsService.deleteEvents(event);
    }

    @ResponseBody
    public List<Event> findNearbyEvents(@RequestBody Double longitude, @RequestBody Double latitude,
                                 @RequestBody Double maxDistance) {
        return eventsService.findNearbyEvents(longitude, latitude, maxDistance);
    }


}

package es.uah.events.events.dao;

import es.uah.events.events.model.DistanceCalculator;
import es.uah.events.events.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EventsDAOImpl implements IEventsDAO{
    @Autowired
    IEventsJPA eventsJPA;

    @Override
    public List<Events> findAll() {
        return eventsJPA.findAll();
    }

    @Override
    public List<Events> findEventsByEventName(String eventName) {
        return eventsJPA.findByEventName(eventName);
    }

    @Override
    public List<Events> findEventsByCreateUser(Integer id) {
        return eventsJPA.findByCreateUser(id);
    }

    @Override
    public List<Events> findNearbyEvents(Double longitude, Double latitude, Double maxDistance) {
        List<Events> events = eventsJPA.findAll();

        List<Events> nearbyEvents = events.stream()
                .filter(event -> {
                    double eventLat = event.getLatitude();
                    double eventLon = event.getLongitude();
                    double distance = DistanceCalculator.calculateDistance(longitude, latitude, eventLat, eventLon);
                    return distance <= maxDistance;
                })
                .toList();
        return nearbyEvents;
    }

    @Override
    public Events findEventsById(Integer id) {
        Optional<Events> optional = eventsJPA.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void saveEvents(Events event) {
        eventsJPA.save(event);
    }

    @Override
    public void deleteEvents(Integer id) {
        eventsJPA.deleteById(id);
    }
}

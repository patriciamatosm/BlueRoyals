package es.uah.client.client.frontend;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import es.uah.client.client.controller.EventsController;
import es.uah.client.client.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@UIScope
@Route("index")
public class Index extends VerticalLayout{

    private final EventsController eventsController;

    @Autowired
    public Index(EventsController eventsController) {
        this.eventsController = eventsController;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar);

        Div contentLayout = new Div();
        contentLayout.getStyle().set("margin-top", "60px");  // Offset for the fixed navbar height
        contentLayout.setWidthFull();
        contentLayout.getStyle().set("padding", "20px");

        // Fetch and display nearby events
        List<Event> events = eventsController.findAll();
        events.forEach(event -> {
            Div eventCard = createEventCard(event);
            contentLayout.add(eventCard);
        });

        add(contentLayout);
    }

    private Div createEventCard(Event event) {
        Div eventCard = new Div();
        eventCard.getStyle().set("border", "1px solid #ccc");
        eventCard.getStyle().set("border-radius", "8px");
        eventCard.getStyle().set("padding", "16px");
        eventCard.getStyle().set("margin", "8px");
        eventCard.getStyle().set("box-shadow", "0 2px 4px rgba(0, 0, 0, 0.1)");

        Div eventName = new Div();
        eventName.setText(event.getEventName());
        eventName.getStyle().set("font-size", "20px");
        eventName.getStyle().set("font-weight", "bold");

//        Div eventLocation = new Div();
//        eventLocation.setText("Location: " + event.getLocation());

        Div eventDate = new Div();
        eventDate.setText("Date: " + event.getCreateDate());

        eventCard.add(eventName, eventDate);

        return eventCard;
    }
}

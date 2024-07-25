package es.uah.client.client.views;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import es.uah.client.client.controller.EventsController;
import es.uah.client.client.controller.SubscriptionsController;
import es.uah.client.client.controller.UsersController;
import es.uah.client.client.model.Event;
import es.uah.client.client.model.Subscription;
import es.uah.client.client.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@UIScope
@Route("index")
public class Index extends VerticalLayout{

    private final EventsController eventsController;
    private final UsersController usersController;
    private final SubscriptionsController subscriptionsController;
    private H1  welcomeMessage;

    @Autowired
    public Index(EventsController eventsController, UsersController usersController,
                 SubscriptionsController subscriptionsController) {
        this.eventsController = eventsController;
        this.usersController = usersController;
        this.subscriptionsController = subscriptionsController;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        welcomeMessage = new H1("Nearby Events");

        welcomeMessage.getStyle().set("font-size", "24px");
        welcomeMessage.getStyle().set("font-weight", "bold");
        welcomeMessage.getStyle().set("color", "dark-grey");
        welcomeMessage.getStyle().set("text-align", "center");
        welcomeMessage.getStyle().set("margin-bottom", "20px");

        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar);

        Div contentLayout = new Div();
        contentLayout.getStyle().set("margin-top", "60px");
        contentLayout.setWidthFull();
        contentLayout.getStyle().set("padding", "20px");

        contentLayout.add(welcomeMessage);

        double[] location = UserLocationService.getUserLocation();

        List<Event> events = null;

        if (location != null) {
            double userLat = location[0];
            double userLon = location[1];

            System.out.println("long: " + userLon + " lat: " + userLat);

            events = eventsController.findNearbyEvents(userLat, userLon, 10.0);
        } else {
            Notification.show("Unable to determine your location.");
        }

        Div gridLayout = new Div();
        gridLayout.getStyle().set("display", "grid");
        gridLayout.getStyle().set("grid-template-columns", "repeat(auto-fit, minmax(300px, 1fr))");
        gridLayout.getStyle().set("gap", "16px");

        assert events != null;
        events.forEach(event -> {
            Div eventCard = createEventCard(event);
            gridLayout.add(eventCard);
        });

        contentLayout.add(gridLayout);
        add(contentLayout);
    }

    private Div createEventCard(Event event) {
        Div eventCard = new Div();
        eventCard.addClassName("event-card");

        Div eventName = new Div();
        eventName.setText(event.getEventName());
        eventName.getStyle().set("font-size", "20px");
        eventName.getStyle().set("font-weight", "bold");

        Div eventLocation = new Div();
        eventLocation.setText("Location: " + event.getLocation());

        Div eventDesc = new Div();
        eventDesc.setText("Description: " + event.getDescription());

        Div eventCreateUser = new Div();
        User createUser = usersController.findUsersById(event.getCreateUser());
        eventCreateUser.setText("Host: " + createUser.getName() + " " + createUser.getSurname());

        Div eventDate = new Div();
        eventDate.setText("Event date: " + event.getCreateDate());

        Div maxUser = new Div();
        List<Subscription> subs = subscriptionsController.findByIdEvent(event.getId());
        maxUser.setText("Attendance: " + subs.size() + "/" + event.getMaxUser());

        User user = (User) VaadinSession.getCurrent().getAttribute(User.class);

        Button subscribeButton = new Button("Subscribe", e -> {
            subscribeToEvent(event.getId(), user.getId());
        });
        subscribeButton.getStyle().set("margin-left", "auto");
        subscribeButton.getStyle().set("background-color", "green");
        subscribeButton.getStyle().set("color", "white");

        eventCard.add(eventName, eventDesc, eventCreateUser, eventDate, eventLocation, maxUser, subscribeButton);

        return eventCard;
    }

    private void subscribeToEvent(Integer eventId, Integer userId) {
        try {
            Subscription s = new Subscription(eventId, userId, false);
            System.out.println(s.getIdEvent() + " " +  s.getIdUser());
            subscriptionsController.subscribe(s);
            Notification.show("Successfully subscribed to the event!");
        } catch (Exception e) {
            Notification.show("Failed to subscribe to the event.");
        }
    }
}

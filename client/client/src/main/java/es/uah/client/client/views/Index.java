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
    public Index(EventsController eventsController, UsersController usersController, SubscriptionsController subscriptionsController) {
        this.eventsController = eventsController;
        this.usersController = usersController;
        this.subscriptionsController = subscriptionsController;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        welcomeMessage = new H1("Nearby Events.");

        welcomeMessage.getStyle().set("font-size", "24px");
        welcomeMessage.getStyle().set("font-weight", "bold");
        welcomeMessage.getStyle().set("color", "dark-grey");
        welcomeMessage.getStyle().set("text-align", "left");
        welcomeMessage.getStyle().set("margin-bottom", "20px");

        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar);

        Div contentLayout = new Div();
        contentLayout.getStyle().set("margin-top", "60px");
        contentLayout.setWidthFull();
        contentLayout.getStyle().set("padding", "20px");

        contentLayout.add(welcomeMessage);

        // TODO: Nearby Events
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

        FlexLayout eventInfoLayout = new FlexLayout();
        eventInfoLayout.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        eventInfoLayout.setWidthFull();

        eventInfoLayout.add(eventName, eventDesc, eventCreateUser, eventDate, eventLocation, maxUser, subscribeButton);

        eventCard.add(eventInfoLayout);

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

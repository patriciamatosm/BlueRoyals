package es.uah.client.client.views;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import es.uah.client.client.controller.EventsController;
import es.uah.client.client.controller.SubscriptionsController;
import es.uah.client.client.controller.UsersController;
import es.uah.client.client.model.Event;
import es.uah.client.client.model.Subscription;
import es.uah.client.client.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@UIScope
@PageTitle("My Subscriptions")
@CssImport("./styles/shared-styles.css")
@Route("subscriptions")
public class Subscriptions extends VerticalLayout  implements  AfterNavigationObserver{

    private final EventsController eventsController;
    private final UsersController usersController;
    private final SubscriptionsController subscriptionsController;
    private H1  welcomeMessage;

    public Subscriptions(EventsController eventsController, UsersController usersController, SubscriptionsController subscriptionsController) {
        this.eventsController = eventsController;
        this.usersController = usersController;
        this.subscriptionsController = subscriptionsController;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        refreshSubscriptions();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent  event) {
        handleUserEnteringView();
    }

    private void handleUserEnteringView() {
        UI.getCurrent().navigate("subscriptions");
        refreshSubscriptions();
    }

    public String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return date.format(formatter);
    }

    private Div createEventCard(Event event) {
        Div eventCard = new Div();
        eventCard.addClassName("event-card");
        eventCard.getStyle().set("padding", "20px")
                .set("border", "1px solid #ddd")
                .set("border-radius", "8px")
                .set("box-shadow", "0px 4px 8px rgba(0, 0, 0, 0.1)")
                .set("width", "400px")
                .set("overflow", "hidden");

        Div eventName = new Div();
        eventName.setText(event.getEventName());
        eventName.getStyle().set("font-size", "20px");
        eventName.getStyle().set("font-weight", "bold").set("margin-bottom", "10px");;

        Div eventLocation = new Div();
        eventLocation.setText("Location: " + event.getLocation());
        eventLocation.getStyle().set("margin-bottom", "10px");


        Div eventDesc = new Div();
        eventDesc.setText("Description: " + event.getDescription());
        eventDesc.getStyle().set("margin-bottom", "10px");


        Div eventCreateUser = new Div();
        User createUser = usersController.findUsersById(event.getCreateUser());
        eventCreateUser.setText("Host: " + createUser.getName() + " " + createUser.getSurname());
        eventCreateUser.getStyle().set("margin-bottom", "10px");

        Div eventDate = new Div();
        LocalDate eventLocalDate = event.getEventDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Convert Date to LocalDate
        String formattedDate = formatDate(eventLocalDate);
        eventDate.setText("Event date: " + formattedDate);
//        eventDate.setText("Event date: " + event.getCreateDate());
        eventDate.getStyle().set("margin-bottom", "10px");

        Div maxUser = new Div();
        List<Subscription> subs = subscriptionsController.findByIdEvent(event.getId());
        maxUser.setText("Attendance: " + subs.size() + "/" + event.getMaxUser());
        maxUser.getStyle().set("margin-bottom", "10px");

        User user = (User) VaadinSession.getCurrent().getAttribute(User.class);
        Button unsubscribeButton = new Button("Unsubscribe", e -> {
            unsubscribeToEvent(event.getId(), user.getId());
        });
        unsubscribeButton.getStyle().set("margin-top", "10px");

        unsubscribeButton.addClassName("unsubscribe-button");

        if (event.getImage() != null && !event.getImage().isEmpty()) {
            String imageUrl = "/files/" + event.getImage().substring(event.getImage().lastIndexOf("/") + 1);
            System.out.println("Path= " + imageUrl);

            Image eventImage = new Image(imageUrl, "Event Image");
            eventImage.setWidth("100%");
            eventImage.setHeight("auto");

            eventImage.getStyle().set("border-radius", "8px")
                    .set("box-shadow", "0px 4px 8px rgba(0, 0, 0, 0.1)")
                    .set("object-fit", "cover");

            Div imageContainer = new Div();
            imageContainer.add(eventImage);
            imageContainer.getStyle().set("width", "100%")
                    .set("overflow", "hidden")
                    .set("margin-bottom", "10px");

            eventCard.add(imageContainer, eventName, eventDesc, eventCreateUser, eventDate, eventLocation, maxUser, unsubscribeButton);

        } else {
            eventCard.add(eventName, eventDesc, eventCreateUser, eventDate, eventLocation, maxUser, unsubscribeButton);
        }

        return eventCard;
    }

    private void refreshSubscriptions() {
        removeAll();

        welcomeMessage = new H1("My Subscriptions");

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

        User user = (User) VaadinSession.getCurrent().getAttribute(User.class);

        List<Event> events = eventsController.findEventsByUser(user);
        System.out.println("Eventos: " + events);

        Div gridLayout = new Div();
        gridLayout.getStyle().set("display", "grid");
        gridLayout.getStyle().set("grid-template-columns", "repeat(auto-fit, minmax(300px, 1fr))");
        gridLayout.getStyle().set("gap", "16px");


        if (events.isEmpty()) {

            Div noEventsMessageContainer = new Div();
            noEventsMessageContainer.getStyle().set("text-align", "center");
            noEventsMessageContainer.getStyle().set("margin", "20px 0");

            Span noEventsMessage = new Span("No subscriptions found.");
            noEventsMessage.getStyle().set("font-size", "18px");
            noEventsMessage.getStyle().set("color", "#ff0000");
            noEventsMessage.getStyle().set("font-weight", "bold");

            noEventsMessageContainer.add(noEventsMessage);
            contentLayout.add(noEventsMessageContainer);
        } else {
            events.forEach(event -> {
                Div eventCard = createEventCard(event);
                gridLayout.add(eventCard);
            });
        }

        contentLayout.add(gridLayout);
        add(contentLayout);
    }


    private void unsubscribeToEvent(Integer eventId, Integer userId) {
        try {
            Subscription s = subscriptionsController.findByIdEventIdUser(eventId, userId);
            System.out.println("Unsubscribing: " + s);
            subscriptionsController.unsubscribe(s);

            refreshSubscriptions();

            Notification.show("Unsubscribed from the event.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Notification.show("Failed to unsubscribe from the event.");
        }
    }

}

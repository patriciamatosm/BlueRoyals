package es.uah.client.client.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import es.uah.client.client.controller.EventsController;
import es.uah.client.client.controller.SubscriptionsController;
import es.uah.client.client.controller.UsersController;
import es.uah.client.client.model.Event;
import es.uah.client.client.model.Subscription;
import es.uah.client.client.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@UIScope
@PageTitle("My events")
@CssImport("./styles/shared-styles.css")
@Route("events")
public class ViewEvents extends VerticalLayout implements AfterNavigationObserver {

    private final EventsController eventsController;
    private final UsersController usersController;
    private final SubscriptionsController subscriptionsController;
    private H1  welcomeMessage;

    public ViewEvents(EventsController eventsController, UsersController usersController, SubscriptionsController subscriptionsController) {
        this.eventsController = eventsController;
        this.usersController = usersController;
        this.subscriptionsController = subscriptionsController;


        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        refreshEvents();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        handleUserEnteringView();
    }

    private void handleUserEnteringView() {
        UI.getCurrent().navigate("events");
        refreshEvents();
    }

    private void openCreateEventModal() {
        Dialog createEventDialog = new Dialog();
        createEventDialog.setWidth("600px");
        createEventDialog.setHeight("600px");

        CreateEvent createEventView = new CreateEvent(eventsController);
        createEventDialog.add(createEventView);

        createEventDialog.open();

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

        Button cancelEventButton = new Button("Cancel Event", e -> {
            cancelEvent(event.getId());
        });
        cancelEventButton.getStyle().set("margin-left", "auto");
        cancelEventButton.getStyle().set("background-color", "red");
        cancelEventButton.getStyle().set("color", "white");
        cancelEventButton.getStyle().set("margin-top", "10px");

        if(event.getImage() != null && !event.getImage().isEmpty()) {
            Image eventImage = new Image(event.getImage(), "Event Image");
            eventImage.setWidth("200px");
            eventCard.add(eventName, eventDesc, eventImage, eventCreateUser, eventDate, eventLocation, maxUser, cancelEventButton);

        } else {
            eventCard.add(eventName, eventDesc, eventCreateUser, eventDate, eventLocation, maxUser, cancelEventButton);

        }

        return eventCard;
    }

    private void refreshEvents() {
        removeAll();

        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar);

        welcomeMessage = new H1("My Events");

        welcomeMessage.getStyle().set("font-size", "24px");
        welcomeMessage.getStyle().set("font-weight", "bold");
        welcomeMessage.getStyle().set("color", "dark-grey");
        welcomeMessage.getStyle().set("text-align", "center");
        welcomeMessage.getStyle().set("margin-bottom", "20px");

        Div contentLayout = new Div();
        contentLayout.getStyle().set("margin-top", "60px");
        contentLayout.setWidthFull();
        contentLayout.getStyle().set("padding", "20px");

        contentLayout.add(welcomeMessage);

        Button createEventButton = new Button("Create Event");
        createEventButton.addClickListener(e -> openCreateEventModal());

        Div spacer = new Div();
        spacer.setHeight("20px");

        contentLayout.add(createEventButton);

        contentLayout.add(spacer);

        User user = (User) VaadinSession.getCurrent().getAttribute(User.class);
        System.out.println("User session: " + user);

        List<Event> events = eventsController.findEventsByUserCreated(user);
        System.out.println("Eventos: " + events);

        Div gridLayout = new Div();
        gridLayout.getStyle().set("display", "grid");
        gridLayout.getStyle().set("grid-template-columns", "repeat(auto-fit, minmax(300px, 1fr))");
        gridLayout.getStyle().set("gap", "16px");


        if (events.isEmpty()) {

            Div noEventsMessageContainer = new Div();
            noEventsMessageContainer.getStyle().set("text-align", "center");
            noEventsMessageContainer.getStyle().set("margin", "20px 0");

            Span noEventsMessage = new Span("No events found.");
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

    private void cancelEvent(Integer eventId) {
        try {
            System.out.println("Canceling: " + eventId);
            eventsController.delete(eventId);

            refreshEvents();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Notification.show("Failed to cancel the event.");
        }
    }

}
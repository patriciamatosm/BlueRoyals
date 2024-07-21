package es.uah.client.client.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
public class ViewEvents extends VerticalLayout {

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

        welcomeMessage = new H1("My Events.");

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

        Button createEventButton = new Button("Create Event");
        createEventButton.addClickListener(e -> openCreateEventModal());

        contentLayout.add(createEventButton);

        User user = (User) VaadinSession.getCurrent().getAttribute(User.class);

        List<Event> events = eventsController.findEventsByUser(user);
        System.out.println("Eventos: " + events);


        events.forEach(event -> {
            Div eventCard = createEventCard(event);
            contentLayout.add(eventCard);
        });

        add(contentLayout);
    }

    private void openCreateEventModal() {
        Dialog createEventDialog = new Dialog();
        createEventDialog.setWidth("600px");
        createEventDialog.setHeight("600px");

        CreateEvent createEventView = new CreateEvent(new EventsController());
        createEventDialog.add(createEventView);

        createEventDialog.open();
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

        FlexLayout eventInfoLayout = new FlexLayout();
        eventInfoLayout.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        eventInfoLayout.setWidthFull();

        eventInfoLayout.add(eventName, eventDesc, eventCreateUser, eventDate, eventLocation, maxUser);

        eventCard.add(eventInfoLayout);

        return eventCard;
    }
}
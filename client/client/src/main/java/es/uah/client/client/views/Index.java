package es.uah.client.client.views;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@UIScope
@Route("index")
public class Index extends VerticalLayout{

    private final EventsController eventsController;
    private final UsersController usersController;
    private final SubscriptionsController subscriptionsController;
    private H1  welcomeMessage;
    private TextField distanceField;
    private Div eventsContainer;
    Double maxDistance = 10.0;

    @Autowired
    public Index(EventsController eventsController, UsersController usersController,
                 SubscriptionsController subscriptionsController) {
        this.eventsController = eventsController;
        this.usersController = usersController;
        this.subscriptionsController = subscriptionsController;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);



        refreshEvents();

    }

    private void refreshEvents(){
        removeAll();


        welcomeMessage = new H1("Nearby Events");

        welcomeMessage.getStyle().set("font-size", "24px");
        welcomeMessage.getStyle().set("font-weight", "bold");
        welcomeMessage.getStyle().set("color", "dark-grey");
        welcomeMessage.getStyle().set("text-align", "center");
        welcomeMessage.getStyle().set("margin-bottom", "20px");

        distanceField = new TextField("Max Distance (km)");
        distanceField.setWidth("200px");
        distanceField.setPlaceholder("Enter distance in km");
        distanceField.getStyle().set("margin-right", "10px");

        Button filterButton = new Button("Filter", e -> updateEvents());
        filterButton.setWidth("100px");
        filterButton.getStyle().set("margin-top", "10px");

        HorizontalLayout filterLayout = new HorizontalLayout(distanceField, filterButton);
        filterLayout.setAlignItems(Alignment.BASELINE);
        filterLayout.setSpacing(true);

        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar);

        Div contentLayout = new Div();
        contentLayout.getStyle().set("margin-top", "60px");
        contentLayout.setWidthFull();
        contentLayout.getStyle().set("padding", "20px");

        contentLayout.add(welcomeMessage);

        double[] location = UserLocationService.getUserLocation();

        List<Event> events = null;

        Div gridLayout = new Div();
        gridLayout.getStyle().set("display", "grid");
        gridLayout.getStyle().set("grid-template-columns", "repeat(auto-fit, minmax(300px, 1fr))");
        gridLayout.getStyle().set("gap", "16px");

        Div noEventsMessageContainer = null;

        if (location != null) {
            double userLat = location[0];
            double userLon = location[1];

            System.out.println("long: " + userLon + " lat: " + userLat);

            //Max distance
//            double maxDistance = 10.0;
            events = eventsController.findNearbyEvents(userLat, userLon, maxDistance);
            System.out.println("MaxD: " + maxDistance);
            System.out.println("events: " + events);



            if (events.isEmpty()) {
                noEventsMessageContainer = new Div();
                noEventsMessageContainer.getStyle().set("text-align", "center");
                noEventsMessageContainer.getStyle().set("margin", "20px 0");

                Span noEventsMessage = new Span("No events found within " + maxDistance + " km.");
                noEventsMessage.getStyle().set("font-size", "18px");
                noEventsMessage.getStyle().set("color", "#ff0000");
                noEventsMessage.getStyle().set("font-weight", "bold");

                noEventsMessageContainer.add(noEventsMessage);

            } else {
                events.forEach(event -> {
                    Div eventCard = createEventCard(event);
                    gridLayout.add(eventCard);
                });
            }
        } else {
            Notification.show("Unable to determine your location.");
        }
        if(noEventsMessageContainer == null) {
            contentLayout.add(filterLayout, gridLayout);
        } else {
            contentLayout.add(filterLayout, noEventsMessageContainer, gridLayout);
        }

        add(contentLayout);
    }

    private void updateEvents() {
        String distanceStr = distanceField.getValue();

        try {
            maxDistance = Double.parseDouble(distanceStr);
        } catch (NumberFormatException e) {
            Notification.show("Invalid distance. Please enter a number.");
            return;
        }
        refreshEvents();
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
        eventName.getStyle().set("font-size", "20px")
                .set("font-weight", "bold")
                .set("margin-bottom", "10px");

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

        Button subscribeButton;

        Subscription s = subscriptionsController.findByIdEventIdUser(event.getId(), user.getId());
        System.out.println("sub: " + s);
        if( (subs.size() < event.getMaxUser())) {

            if (s != null) {
                subscribeButton = new Button("Unsubscribe", e -> {
                    unsubscribeToEvent(s);
                });
                subscribeButton.getStyle().set("margin-left", "auto");
                subscribeButton.getStyle().set("background-color", "red");
                subscribeButton.getStyle().set("color", "white");
            } else {
                subscribeButton = new Button("Subscribe", e -> {
                    subscribeToEvent(event.getId(), user.getId());
                });
                subscribeButton.getStyle().set("margin-left", "auto");
                subscribeButton.getStyle().set("background-color", "green");
                subscribeButton.getStyle().set("color", "white");
            }
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

                eventCard.add(imageContainer, eventName, eventDesc, eventCreateUser, eventDate, eventLocation, maxUser, subscribeButton);

            } else {
                eventCard.add(eventName, eventDesc, eventCreateUser, eventDate, eventLocation, maxUser, subscribeButton);
            }

        } else {
            if (s != null) {
                subscribeButton = new Button("Unsubscribe", e -> {
                    unsubscribeToEvent(s);
                });
                subscribeButton.getStyle().set("margin-left", "auto");
                subscribeButton.getStyle().set("background-color", "red");
                subscribeButton.getStyle().set("color", "white");

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

                    eventCard.add(imageContainer, eventName, eventDesc, eventCreateUser, eventDate, eventLocation, maxUser, subscribeButton);

                } else {
                    eventCard.add(eventName, eventDesc, eventCreateUser, eventDate, eventLocation, maxUser, subscribeButton);
                }
            } else {

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

                    eventCard.add(imageContainer, eventName, eventDesc, eventCreateUser, eventDate, eventLocation, maxUser);

                } else {
                    eventCard.add(eventName, eventDesc, eventCreateUser, eventDate, eventLocation, maxUser);
                }
            }

        }



        return eventCard;
    }

    private void unsubscribeToEvent(Subscription s) {
        try {
            System.out.println("Unsubscribing: " + s);
            subscriptionsController.unsubscribe(s);
            Notification.show("Unsubscribed from the event.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Notification.show("Failed to unsubscribe from the event.");
        }
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

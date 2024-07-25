package es.uah.client.client.views;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import es.uah.client.client.controller.EventsController;
import es.uah.client.client.model.User;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;
import es.uah.client.client.model.Event;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.util.Date;

import static java.time.LocalDate.now;

@Component
@UIScope
@PageTitle("Create Event")
@CssImport("./styles/shared-styles.css")
@Route("create-event")
public class CreateEvent extends VerticalLayout {

    private final EventsController eventsController;
    private H1  welcomeMessage;

    public CreateEvent(EventsController eventsController) {
        this.eventsController = eventsController;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar);


        Div contentContainer = new Div();
        contentContainer.addClassName("centered-content");
        contentContainer.setWidthFull();

        welcomeMessage = new H1("Create your own event");

        welcomeMessage.getStyle().set("font-size", "24px");
        welcomeMessage.getStyle().set("font-weight", "bold");
        welcomeMessage.getStyle().set("color", "dark-grey");
        welcomeMessage.getStyle().set("text-align", "left");
        welcomeMessage.getStyle().set("margin-bottom", "20px");

        FormLayout formLayout = createFormLayout();

        contentContainer.add(welcomeMessage, formLayout);

        add(contentContainer);


    }

    private FormLayout createFormLayout() {
        TextField nameField = new TextField("Event Name");
        TextArea descriptionField = new TextArea("Description");
        DatePicker eventDateField = new DatePicker("Event Date");
        TextField locationField = new TextField("Location");
        TextField maxUsersField = new TextField("Max Users");

        Button saveButton = new Button("Save");
        saveButton.addClickListener(e -> saveEvent(nameField, descriptionField, eventDateField, locationField, maxUsersField));
        saveButton.getStyle().set("background-color", "green");
        saveButton.getStyle().set("color", "white");
        saveButton.getStyle().set("margin-left", "auto");


        Button closeButton = new Button("Close", e -> ((Dialog) this.getParent().get()).close());
        closeButton.getStyle().set("background-color", "red");
        closeButton.getStyle().set("color", "white");
        closeButton.getStyle().set("margin-right", "auto");


        FormLayout formLayout = new FormLayout();

        HorizontalLayout nameAndDateLayout = new HorizontalLayout();
        nameAndDateLayout.add(nameField, eventDateField);
        nameAndDateLayout.setWidthFull();
        nameAndDateLayout.setSpacing(true);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(closeButton, saveButton);
        buttonsLayout.setWidthFull();
        buttonsLayout.setSpacing(true);
        buttonsLayout.getStyle().set("justify-content", "center");

        formLayout.add(nameAndDateLayout, descriptionField, locationField, maxUsersField, buttonsLayout);

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );

        return formLayout;
    }

    private void saveEvent(TextField nameField, TextArea descriptionField, DatePicker eventDateField, TextField locationField, TextField maxUsersField) {
        String name = nameField.getValue();
        String description = descriptionField.getValue();
        LocalDate eventDate = eventDateField.getValue();
        String location = locationField.getValue();
        int maxUsers = Integer.parseInt(maxUsersField.getValue());

        Date eventDateAsDate = DateUtils.convertToDateViaInstant(eventDate);

        User user = (User) VaadinSession.getCurrent().getAttribute(User.class);

        Event event = new Event();
        event.setEventName(name);
        event.setDescription(description);
        event.setEventDate(eventDateAsDate);
        event.setLocation(location);
        event.setMaxUser(maxUsers);
        event.setCreateUser(user.getId());
        Date createDate = DateUtils.convertToDateViaInstant(LocalDate.now());
        event.setCreateDate(createDate);
        event.setDelete(false);

        System.out.println(event);
        Boolean result = eventsController.saveEvent(event);

        if(result) Notification.show("Event saved!");

        getUI().ifPresent(ui -> ui.navigate("index"));
    }
}
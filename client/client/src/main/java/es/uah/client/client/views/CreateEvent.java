package es.uah.client.client.views;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import es.uah.client.client.controller.EventsController;
import es.uah.client.client.model.User;
import org.springframework.stereotype.Component;
import es.uah.client.client.model.Event;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

import static java.time.LocalDate.now;

@Component
@UIScope
@PageTitle("Create Event")
@CssImport("./styles/shared-styles.css")
@Route("create-event")
public class CreateEvent extends VerticalLayout {

    private final EventsController eventsController;
    private H1 welcomeMessage;
    private String uploadedImagePath;

    public CreateEvent(EventsController eventsController) {
        this.eventsController = eventsController;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        welcomeMessage = new H1("Create your own event");

        welcomeMessage.getStyle().set("font-size", "24px");
        welcomeMessage.getStyle().set("font-weight", "bold");
        welcomeMessage.getStyle().set("color", "dark-grey");
        welcomeMessage.getStyle().set("text-align", "left");
        welcomeMessage.getStyle().set("margin-bottom", "20px");

        add(welcomeMessage);

        Div contentContainer = new Div();
        contentContainer.addClassName("centered-content");
        contentContainer.setWidthFull();

        FormLayout formLayout = createFormLayout();

        contentContainer.add(formLayout);

        add(contentContainer);

    }

    private FormLayout createFormLayout() {
        TextField nameField = new TextField("Event Name");
        TextArea descriptionField = new TextArea("Description");
        DatePicker eventDateField = new DatePicker("Event Date");
        TextField locationField = new TextField("Location");
        TextField maxUsersField = new TextField("Max Users");

        Upload upload = getUpload();


        Button saveButton = new Button("Save");
        saveButton.addClickListener(e -> {
            saveEvent(nameField, descriptionField, eventDateField, locationField, maxUsersField);
            ((Dialog) this.getParent().get()).close();
            getUI().ifPresent(ui -> ui.navigate("index"));
        });
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

//        HorizontalLayout uploadLayout = new HorizontalLayout();
//        uploadLayout.add(upload);
//        uploadLayout.setWidthFull();
//        uploadLayout.setSpacing(true);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(closeButton, saveButton);
        buttonsLayout.setWidthFull();
        buttonsLayout.setSpacing(true);
        buttonsLayout.getStyle().set("justify-content", "center");

        formLayout.add(nameAndDateLayout, upload, descriptionField, locationField, maxUsersField, buttonsLayout);

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );

        return formLayout;
    }

    private Upload getUpload() {
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/jpg");
        upload.setMaxFiles(1);
        upload.setMaxFileSize(1024 * 1024); // 1MB max size

        Span uploadHint = new Span("Upload");
        upload.setDropLabel(uploadHint);

        upload.addSucceededListener(event -> {
            InputStream inputStream = buffer.getInputStream();
            String fileName = event.getFileName();
            uploadedImagePath = eventsController.uploadEventImage(fileName, inputStream);
            Notification.show("Image uploaded successfully! File name: " + fileName);
        });

        upload.addFailedListener(event -> {
            Notification.show("Image upload failed: " + event.getReason().getMessage());
        });
        return upload;
    }


    private void saveEvent(TextField nameField, TextArea descriptionField, DatePicker eventDateField, TextField locationField, TextField maxUsersField) {
        String name = nameField.getValue();
        String description = descriptionField.getValue();
        LocalDate eventDate = eventDateField.getValue();
        String location = locationField.getValue();

        if (uploadedImagePath == null || uploadedImagePath.isEmpty()) {
            Notification.show("Please upload an image before saving the event.");
            return;
        }

        int maxUsers;
        if(maxUsersField.getValue().isEmpty()) {
            maxUsers = 0;
        } else {
            maxUsers = Integer.parseInt(maxUsersField.getValue());
        }

        if(name.isEmpty() || description.isEmpty() || location.isEmpty() || maxUsers == 0) {
            Notification.show("Please fill all of the fields.");

        } else {

        double[] coordinates = GeocodingService.getCoordinates(location);

        System.out.println("coordinates: " + Arrays.toString(coordinates));


        if (coordinates == null) {
            Notification.show("Unable to geocode location. Please try again.");
            return;
        }

        double latitude = coordinates[0];
        double longitude = coordinates[1];

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
        event.setLatitude(latitude);
        event.setLongitude(longitude);
        event.setImage(uploadedImagePath);

        System.out.println(event);

            Boolean result = eventsController.saveEvent(event);

            if(result) Notification.show("Event saved!");
        }


    }
}
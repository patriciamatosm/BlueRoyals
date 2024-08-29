package es.uah.client.client.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import es.uah.client.client.controller.EventsController;
import es.uah.client.client.controller.GroupChatsController;
import es.uah.client.client.controller.SubscriptionsController;
import es.uah.client.client.model.Event;
import es.uah.client.client.model.GroupChats;
import es.uah.client.client.model.User;
import org.springframework.stereotype.Component;

@Component
@UIScope
@PageTitle("Create Chat")
@CssImport("./styles/shared-styles.css")
@Route("create-chat")
public class CreateChat extends VerticalLayout {
    private final GroupChatsController chatsController;
    private final EventsController eventsController;
    private User currentUser;

    private H1 welcomeMessage;

    public CreateChat(GroupChatsController groupChatsController, EventsController eventsController) {
        this.chatsController = groupChatsController;
        this.eventsController = eventsController;

        currentUser = (User) VaadinSession.getCurrent().getAttribute(User.class);


        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        welcomeMessage = new H1("Create chat");

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
        TextField chatNameField = new TextField("Chat Name");
        ComboBox<Event> eventComboBox = new ComboBox<>("Event");
        eventComboBox.setItems(eventsController.findEventsByUser(currentUser));
        eventComboBox.setItemLabelGenerator(Event::getEventName);

        Button saveButton = new Button("Save", e -> {
            String chatName = chatNameField.getValue();
            Event selectedEvent = eventComboBox.getValue();
            if (!chatName.isEmpty() && selectedEvent != null) {
                GroupChats newChat = chatsController.createChat(chatName, selectedEvent, currentUser);
                if (newChat != null) {
                    Notification.show("Chat created successfully!");
                } else {
                    Notification.show("Failed to create chat.");
                }
                ((Dialog) this.getParent().get()).close();
                getUI().ifPresent(ui -> ui.navigate("chats"));
            }
        });
        saveButton.getStyle().set("background-color", "green").set("color", "white");

        Button closeButton = new Button("Close", e -> ((Dialog) this.getParent().get()).close());
        closeButton.getStyle().set("background-color", "red");
        closeButton.getStyle().set("color", "white");
        closeButton.getStyle().set("margin-right", "auto");

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(closeButton, saveButton);
        buttonsLayout.setWidthFull();
        buttonsLayout.setSpacing(true);
        buttonsLayout.getStyle().set("justify-content", "center");

        FormLayout formLayout = new FormLayout();

        formLayout.add(chatNameField, eventComboBox, buttonsLayout);

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );

        return formLayout;
    }

}

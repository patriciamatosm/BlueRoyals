package es.uah.client.client.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import es.uah.client.client.controller.EventsController;
import es.uah.client.client.controller.GroupChatsController;
import es.uah.client.client.controller.UsersController;
import es.uah.client.client.model.ChatMessages;
import es.uah.client.client.model.Event;
import es.uah.client.client.model.GroupChats;
import es.uah.client.client.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@UIScope
@PageTitle("Chats")
@CssImport("./styles/shared-styles.css")
@Route("chats")
public class ChatsView extends VerticalLayout {


    private final GroupChatsController chatsController;

    private final EventsController eventsController;

    private final UsersController usersController;

    private User currentUser;

    private H1  welcomeMessage;

    public ChatsView(GroupChatsController chatsController, EventsController eventsController, UsersController usersController) {
        this.chatsController = chatsController;
        this.eventsController = eventsController;
        this.usersController = usersController;

        currentUser = (User) VaadinSession.getCurrent().getAttribute(User.class);

        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar);

        welcomeMessage = new H1("My Chats");

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

        Button createChatButton = new Button("Create Chat", e -> openCreateChatDialog());
        contentLayout.add(createChatButton);

        Div spacer = new Div();
        spacer.setHeight("20px");

        contentLayout.add(spacer);

        Grid<GroupChats> chatGrid = new Grid<>(GroupChats.class, false);
        chatGrid.addColumn(GroupChats::getChatName).setHeader("Chat Name");
        chatGrid.addColumn(chat -> eventsController.findEventsById(chat.getIdEvent()).getEventName()).setHeader("Event");
        chatGrid.addComponentColumn(chat -> new Button("Open Chat", click -> openChatDialog(chat)))
                .setHeader("Actions");



        List<GroupChats> userChats = chatsController.getChatsByUser(currentUser.getId());
        chatGrid.setItems(userChats);

        contentLayout.add(chatGrid);

        add(contentLayout);
    }

    private void openCreateChatDialog() {
        Dialog createChatDialog = new Dialog();
        createChatDialog.setWidth("400px");

        welcomeMessage = new H1("Create chat");

        welcomeMessage.getStyle().set("font-size", "24px");
        welcomeMessage.getStyle().set("font-weight", "bold");
        welcomeMessage.getStyle().set("color", "dark-grey");
        welcomeMessage.getStyle().set("text-align", "left");
        welcomeMessage.getStyle().set("margin-bottom", "20px");

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
                createChatDialog.close();
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


        createChatDialog.add(new VerticalLayout(welcomeMessage, chatNameField, eventComboBox, buttonsLayout));
        createChatDialog.open();
    }

    private void openChatDialog(GroupChats chat) {
        Dialog chatDialog = new Dialog();
        chatDialog.setWidth("600px");
        chatDialog.setHeight("400px");

        VerticalLayout messageLayout = new VerticalLayout();
        messageLayout.setWidthFull();

        List<ChatMessages> messages = chatsController.getMessagesByChat(chat.getId());

        for (ChatMessages message : messages) {
            Div messageDiv = new Div();
            messageDiv.setText(usersController.findUsersById(message.getIdUser()).getName() + ": " + message.getTextMsg() +
                    " (" + message.getDate() + ")");
            if (message.getIdUser().equals(currentUser.getId())) {
                messageDiv.getStyle().set("text-align", "right").set("background-color", "#e3f2fd");
            } else {
                messageDiv.getStyle().set("text-align", "left").set("background-color", "#f1f8e9");
            }
            messageDiv.getStyle().set("padding", "10px").set("margin", "5px 0");
            messageLayout.add(messageDiv);
        }

        Button closeButton = new Button("Close", e -> chatDialog.close());
        chatDialog.add(messageLayout, closeButton);
        chatDialog.open();
    }
}

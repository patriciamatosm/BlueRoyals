package es.uah.client.client.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
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
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    private final int MAX_LENGTH = 255;

    public ChatsView(GroupChatsController chatsController, EventsController eventsController, UsersController usersController) {
        this.chatsController = chatsController;
        this.eventsController = eventsController;
        this.usersController = usersController;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        refreshChats();


    }

    private void refreshChats(){
        currentUser = VaadinSession.getCurrent().getAttribute(User.class);

        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar);

        H1 welcomeMessage = new H1("My Chats");

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
        createChatButton.getStyle().set("background-color", "#6200ea").set("color", "white");
        contentLayout.add(createChatButton);

        Div spacer = new Div();
        spacer.setHeight("20px");

        contentLayout.add(spacer);

        Grid<GroupChats> chatGrid = new Grid<>(GroupChats.class, false);
        chatGrid.addColumn(GroupChats::getChatName).setHeader("Chat Name");
        chatGrid.addColumn(chat -> eventsController.findEventsById(chat.getIdEvent()).getEventName()).setHeader("Event");
        chatGrid.addComponentColumn(chat -> new Button("Open Chat", click -> openChatDialog(chat)))
                .setHeader("Actions");


        List<Event> events = eventsController.findEventsByUser(currentUser);

        System.out.println("here");
        List<GroupChats> userChats = chatsController.getChatByEvent(events);
        chatGrid.setItems(userChats);

        contentLayout.add(chatGrid);

        add(contentLayout);
    }

    private void openCreateChatDialog() {
        Dialog createChatDialog = new Dialog();
        createChatDialog.setWidth("600px");
        createChatDialog.setHeight("600px");

        CreateChat createChat = new CreateChat(chatsController, eventsController);
        createChatDialog.add(createChat);

        createChatDialog.open();
    }

    public String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return date.format(formatter);
    }

    private HorizontalLayout createMessageComponent(ChatMessages message, User currentUser, boolean now) {
        Div messageDiv = new Div();
        String date;

        if(now) {
            date = "now";
        } else {
            LocalDate eventLocalDate = message.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Convert Date to LocalDate
            date = formatDate(eventLocalDate);
        }

        messageDiv.setText(usersController.findUsersById(message.getIdUser()).getName() + ": " + message.getTextMsg() +
                " (" + date + ")");

        HorizontalLayout messageLayout = new HorizontalLayout(messageDiv);
        messageLayout.setWidthFull();
        if (message.getIdUser().equals(currentUser.getId())) {
            messageLayout.setJustifyContentMode(JustifyContentMode.END);
            messageDiv.getStyle().set("background-color", "#e3f2fd");
        } else {
            messageLayout.setJustifyContentMode(JustifyContentMode.START);
            messageDiv.getStyle().set("background-color", "#f1f8e9");
        }
        messageDiv.getStyle().set("padding", "10px").set("margin", "5px 0");
        return messageLayout;
    }

    private void openChatDialog(GroupChats chat) {
        Dialog chatDialog = new Dialog();
        chatDialog.setWidth("800px");
        chatDialog.setHeight("80vh");
        chatDialog.setResizable(true);

        Div messagesContainer = new Div();
        messagesContainer.setHeight("calc(100% - 150px)");
        messagesContainer.getStyle().set("overflow-y", "auto");
        messagesContainer.setWidthFull();
        messagesContainer.addClassName("messages-container");

        List<ChatMessages> messages = chatsController.getMessagesByChat(chat.getId());
        System.out.println("mensajes= " + messages);

        H3 welcomeMessage2 = new H3("Chat: " + chat.getChatName());

        welcomeMessage2.getStyle().set("font-size", "24px");
        welcomeMessage2.getStyle().set("font-weight", "bold");
        welcomeMessage2.getStyle().set("color", "dark-grey");
        welcomeMessage2.getStyle().set("text-align", "left");
        welcomeMessage2.getStyle().set("margin-bottom", "20px");


        if(messages != null && !messages.isEmpty()){
            for (ChatMessages message : messages) {
                messagesContainer.add(createMessageComponent(message, currentUser, false));
            }
        }

        // Add text area and send button for sending messages
        TextArea messageInput = new TextArea();
        messageInput.setPlaceholder("Type your message...");
        messageInput.setWidthFull();
        messageInput.setMaxLength(MAX_LENGTH);

        messageInput.addValueChangeListener(event -> {
            int remaining = MAX_LENGTH - event.getValue().length();
            messageInput.setHelperText(remaining + " characters left");
        });

        Button sendButton = new Button("Send", e -> {
            String content = messageInput.getValue();
            if (content.trim().length() > MAX_LENGTH) {
                Notification.show("Message is too long! Maximum length is " + MAX_LENGTH + " characters.");
            }
            else if (!content.trim().isEmpty()) {
                // Save the message using the chatsController
                chatsController.createChatMesage(chat, currentUser, content);
                ChatMessages mNew = new ChatMessages();
                mNew.setTextMsg(content);
                mNew.setIdUser(currentUser.getId());
                messagesContainer.add(createMessageComponent(mNew, currentUser, true));
                messageInput.clear();
            } else {
                Notification.show("Message cannot be empty.");
            }
        });
        sendButton.getStyle().set("background-color", "#4CAF50").set("color", "white");


        HorizontalLayout messageInputLayout = new HorizontalLayout(messageInput, sendButton);
        messageInputLayout.setWidthFull();
        messageInputLayout.setAlignItems(FlexComponent.Alignment.END);
        messageInputLayout.setSpacing(true);

        HorizontalLayout footerLayout = new HorizontalLayout(messageInputLayout);
        footerLayout.setWidthFull();
        footerLayout.setAlignItems(FlexComponent.Alignment.CENTER);


        Button closeButton = new Button("Close", e -> chatDialog.close());
        closeButton.getStyle().set("background-color", "red");
        closeButton.getStyle().set("color", "white");
        closeButton.getStyle().set("margin-right", "auto");

        VerticalLayout mainLayout = new VerticalLayout(welcomeMessage2, messagesContainer, footerLayout, closeButton);
        mainLayout.setPadding(false);
        mainLayout.setSpacing(false);
        mainLayout.setSizeFull();

        chatDialog.add(mainLayout);
        chatDialog.open();
    }
}

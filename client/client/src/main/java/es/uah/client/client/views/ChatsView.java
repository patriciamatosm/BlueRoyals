package es.uah.client.client.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
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

    private H1  welcomeMessage;

    private H3 welcomeMessage2;

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

    private void openChatDialog(GroupChats chat) {
        Dialog chatDialog = new Dialog();
        chatDialog.setWidth("1000px");
        chatDialog.setHeight("1000px");

        VerticalLayout messageLayout = new VerticalLayout();
        messageLayout.setWidthFull();

        List<ChatMessages> messages = chatsController.getMessagesByChat(chat.getId());
        System.out.println("mensajes= " + messages);

        welcomeMessage2 = new H3("Chat: " + chat.getChatName());

        welcomeMessage2.getStyle().set("font-size", "24px");
        welcomeMessage2.getStyle().set("font-weight", "bold");
        welcomeMessage2.getStyle().set("color", "dark-grey");
        welcomeMessage2.getStyle().set("text-align", "left");
        welcomeMessage2.getStyle().set("margin-bottom", "20px");


        if(messages != null && !messages.isEmpty()){
            for (ChatMessages message : messages) {
                if(message != null){
                    Div messageDiv = new Div();

                    LocalDate eventLocalDate = message.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Convert Date to LocalDate
                    String formattedDate = formatDate(eventLocalDate);

                    messageDiv.setText(usersController.findUsersById(message.getIdUser()).getName() + ": " + message.getTextMsg() +
                            " (" + formattedDate + ")");
                    if (message.getIdUser().equals(currentUser.getId())) {
                        messageDiv.getStyle().set("margin-left", "auto").set("background-color", "#e3f2fd");
                    } else {
                        messageDiv.getStyle().set("margin-right", "auto").set("background-color", "#f1f8e9");
                    }
                    messageDiv.getStyle().set("padding", "10px").set("margin", "5px 0");
                    messageLayout.add(messageDiv);
                }
            }
        }

        // Add text area and send button for sending messages
        TextArea messageInput = new TextArea();
        messageInput.setPlaceholder("Type your message...");
        messageInput.setWidthFull();

        Button sendButton = new Button("Send", e -> {
            String content = messageInput.getValue();
            if (!content.trim().isEmpty()) {
                // Save the message using the chatsController
                chatsController.createChatMesage(chat, currentUser, content);
                Div newMessageDiv = new Div();
                newMessageDiv.setText(currentUser.getName() + ": " + content +
                        " (" + "now" + ")");
                newMessageDiv.getStyle().set("text-align", "right").set("background-color", "#e3f2fd")
                        .set("padding", "10px").set("margin", "5px 0");

                messageLayout.add(newMessageDiv);
                messageInput.clear();
            } else {
                Notification.show("Message cannot be empty.");
            }
        });
        sendButton.getStyle().set("background-color", "#4CAF50").set("color", "white");

        HorizontalLayout messageInputLayout = new HorizontalLayout(messageInput, sendButton);
        messageInputLayout.setWidthFull();

        Button closeButton = new Button("Close", e -> chatDialog.close());
        closeButton.getStyle().set("background-color", "red");
        closeButton.getStyle().set("color", "white");
        closeButton.getStyle().set("margin-right", "auto");

        chatDialog.add(welcomeMessage2, messageLayout, messageInputLayout, closeButton);
        chatDialog.open();
    }
}

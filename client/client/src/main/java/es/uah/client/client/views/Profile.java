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
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import es.uah.client.client.controller.UsersController;
import es.uah.client.client.model.Event;
import es.uah.client.client.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.time.LocalDate;
import java.util.Date;

@Component
@UIScope
@PageTitle("My profile")
@CssImport("./styles/shared-styles.css")
@Route("profile")
public class Profile extends VerticalLayout {

    private final UsersController usersController;
    private H1 welcomeMessage;
    private User user;


    public Profile(UsersController usersController) {
        this.usersController = usersController;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        refreshUsers();

    }

    private void refreshUsers() {
        removeAll();

        user = (User) VaadinSession.getCurrent().getAttribute(User.class);
        System.out.println("User session: " + user);

        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar);

        welcomeMessage = new H1("My profile");

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


        Div contentContainer = new Div();
        contentContainer.addClassName("centered-content");
        contentContainer.setWidthFull();

        FormLayout formLayout = createFormLayout();

        contentContainer.add(formLayout);


        add(contentLayout, contentContainer);
    }

    private FormLayout createFormLayout() {
        TextField nameField = new TextField("Name");
        nameField.setValue(user.getName());

        TextField surnameField = new TextField("Surname");
        surnameField.setValue(user.getSurname());

        TextField emailField = new TextField("Email");
        emailField.setValue(user.getEmail());

        PasswordField passwordField = new PasswordField("Password");
        passwordField.setValue(user.getPassword());

        TextField usernameField = new TextField("Username");
        usernameField.setValue(user.getUsername());
        usernameField.setReadOnly(true);

        Button saveButton = new Button("Save");
        saveButton.addClickListener(e -> saveUser(nameField, surnameField, emailField, passwordField));
        saveButton.getStyle().set("background-color", "green");
        saveButton.getStyle().set("color", "white");
        saveButton.getStyle().set("margin-left", "auto");


        FormLayout formLayout = new FormLayout();

        HorizontalLayout nameAndDateLayout = new HorizontalLayout();
        nameAndDateLayout.add(nameField, surnameField);
        nameAndDateLayout.setWidthFull();
        nameAndDateLayout.setSpacing(true);

        HorizontalLayout usernameAndPass = new HorizontalLayout();
        usernameAndPass.add(usernameField, passwordField);
        usernameAndPass.setWidthFull();
        usernameAndPass.setSpacing(true);

        nameAndDateLayout.add(usernameAndPass);

        formLayout.add(nameAndDateLayout, emailField, saveButton);

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );

        return formLayout;
    }

    private void saveUser(TextField nameField, TextField surnameField, TextField emailField,
                          PasswordField passwordField) {
        String name = nameField.getValue();
        String surname = surnameField.getValue();
        String email = emailField.getValue();
        String password = passwordField.getValue();


        if (user == null) {
            Notification.show("No user logged in.");
            return;
        }

        User userUpdated = new User();
        User result = null;

//        COPY OBJECT

        userUpdated.setId(user.getId());
        userUpdated.setUsername(user.getUsername());
        userUpdated.setName(user.getName());
        userUpdated.setSurname(user.getSurname());
        userUpdated.setEmail(user.getEmail());
        userUpdated.setPassword(user.getPassword());
        userUpdated.setDelete(user.getDelete());


        System.out.println("userUpdated before: " + userUpdated);

        // Apply changes
        userUpdated.setName(name);
        userUpdated.setSurname(surname);
        userUpdated.setEmail(email);
        if (!password.isEmpty()) {
            userUpdated.setPassword(password);
        }

        System.out.println("userUpdated after: " +userUpdated);
        usersController.update(userUpdated);

        User newUser = usersController.findUsersById(user.getId());
        System.out.println("newUser type: " + newUser.getClass().getName());
        VaadinSession.getCurrent().setAttribute(User.class, newUser);

        refreshUsers();
    }
}

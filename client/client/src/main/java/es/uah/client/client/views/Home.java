package es.uah.client.client.views;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import es.uah.client.client.controller.UsersController;
import es.uah.client.client.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route("")
public class Home extends VerticalLayout {

    @Autowired
    private UsersController userController;

    private final Div loginFormContainer;
    private final Div registrationFormContainer;
    private H1  welcomeMessage;
    private H2  loginMessage;

    public Home() {
        setSizeFull();
        getStyle().set("padding-top", "50px");
        getStyle().set("background-image", "url('/images/blue_royals_banner.png')");
        //getStyle().set("background-color", "#b7d0e1");
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        welcomeMessage = new H1("Welcome to BlueRoyals!");
        loginMessage = new H2("Log In.");
        loginFormContainer = new Div();
        registrationFormContainer = new Div();
        registrationFormContainer.setVisible(false);

        welcomeMessage.getStyle().set("font-size", "24px");
        welcomeMessage.getStyle().set("font-weight", "bold");
        welcomeMessage.getStyle().set("color", "#0044cc");
        welcomeMessage.getStyle().set("text-align", "center");
        welcomeMessage.getStyle().set("margin-bottom", "20px");

        loginMessage.getStyle().set("font-size", "24px");
        loginMessage.getStyle().set("font-weight", "bold");
        loginMessage.getStyle().set("color", "dark-grey");
        loginMessage.getStyle().set("margin-bottom", "20px");

        loginFormContainer.getStyle().set("max-width", "400px");
        loginFormContainer.getStyle().set("width", "100%");
        loginFormContainer.getStyle().set("padding", "20px");
        loginFormContainer.getStyle().set("box-shadow", "0 2px 10px rgba(0, 0, 0, 0.1)");
        loginFormContainer.getStyle().set("border-radius", "8px");

        registrationFormContainer.getStyle().set("max-width", "400px");
        registrationFormContainer.getStyle().set("width", "100%");
        registrationFormContainer.getStyle().set("padding", "20px");
        registrationFormContainer.getStyle().set("box-shadow", "0 2px 10px rgba(0, 0, 0, 0.1)");
        registrationFormContainer.getStyle().set("border-radius", "8px");


        createLoginForm(loginFormContainer);
        createRegistrationForm(registrationFormContainer);

        add(loginFormContainer, registrationFormContainer);
    }

    private void createLoginForm(Div container) {
        FormLayout loginForm = new FormLayout();

        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        Button loginButton = new Button("Login", event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            try {
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);

                User userLogged = userController.login(user);
                if (userLogged != null) {
                    Notification.show("Login successful");
                    VaadinSession.getCurrent().setAttribute(User.class, userLogged);
                    User uSesssion = (User) VaadinSession.getCurrent().getAttribute(User.class);
                    System.out.println("USER: " + uSesssion);
                    getUI().ifPresent(ui -> ui.navigate("index"));
                } else {
                    Notification.show("Invalid credentials", 3000, Notification.Position.MIDDLE);
                }
            } catch (Exception e) {
                Notification.show("Login failed: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });
        loginButton.getStyle().set("background-color", "#0044cc");
        loginButton.getStyle().set("color", "white");
        loginButton.getStyle().set("margin-bottom", "10px");

        Anchor registerHereLink = new Anchor("#", "Register here");
        registerHereLink.getStyle().set("font-size", "smaller");
        registerHereLink.getStyle().set("display", "block");
        registerHereLink.getStyle().set("text-align", "left");
        registerHereLink.getElement().addEventListener("click", e -> {
            loginFormContainer.setVisible(false);
            registrationFormContainer.setVisible(true);
        });

        loginForm.add(welcomeMessage, loginMessage, usernameField, passwordField, loginButton);

        Div registerLinkDiv = new Div(registerHereLink);
        registerLinkDiv.getStyle().set("text-align", "center");
        loginForm.add(registerLinkDiv);

        container.add(loginForm);
    }

    private void createRegistrationForm(Div container) {
        FormLayout registrationForm = new FormLayout();

        TextField emailField = new TextField("Email");
        TextField usernameField = new TextField("Username");
        TextField nameField = new TextField("Name");
        TextField surnameField = new TextField("Surname");
        PasswordField passwordField = new PasswordField("Password");
        Button registerButton = new Button("Register", event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            String mail = emailField.getValue();
            String name = nameField.getValue();
            String surname = surnameField.getValue();
            try {
                User user = new User(username, mail, password, name, surname);
                User registeredUser = userController.save(user);
                Notification.show("Registration successful for user: " + registeredUser.getUsername());
            } catch (Exception e) {
                Notification.show("Registration failed: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });
        registerButton.getStyle().set("background-color", "#3975F0");
        registerButton.getStyle().set("color", "white");
        registerButton.getStyle().set("margin-bottom", "10px");

        Anchor backButton = new Anchor("#", "Back to login");
        backButton.getStyle().set("font-size", "smaller");
        backButton.getStyle().set("color", "red");
        backButton.getStyle().set("display", "block");
        backButton.getStyle().set("text-align", "left");
        backButton.getElement().addEventListener("click", e -> {
            registrationFormContainer.setVisible(false);
            loginFormContainer.setVisible(true);
        });

        registrationForm.add(nameField, surnameField, usernameField, passwordField, emailField, registerButton);

        Div backButtonDiv = new Div(backButton);
        backButtonDiv.getStyle().set("text-align", "center");
        registrationForm.add(backButtonDiv);

        container.add(registrationForm);
    }

}

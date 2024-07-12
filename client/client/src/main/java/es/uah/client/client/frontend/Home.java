package es.uah.client.client.frontend;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import es.uah.client.client.controller.UsersController;
import es.uah.client.client.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Route("")
public class Home extends VerticalLayout {

    @Autowired
    private UsersController userController;

    public Home() {
        createLoginForm();
    }

    private void createLoginForm() {
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

                Boolean isAuthenticated = userController.login(user);
                if (Boolean.TRUE.equals(isAuthenticated)) {
                    Notification.show("Login successful");
                } else {
                    Notification.show("Invalid credentials", 3000, Notification.Position.MIDDLE);
                }
            } catch (Exception e) {
                Notification.show("Login failed: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });

        loginForm.add(usernameField, passwordField, loginButton);
        add(loginForm);
    }

    /*private void createRegistrationForm() {
        FormLayout registrationForm = new FormLayout();

        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        Button registerButton = new Button("Register", event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            try {
                User user = new User(username, password);
                User registeredUser = restTemplate.postForObject(
                        "http://localhost:8091/users/register",
                        user,
                        User.class
                );
                Notification.show("Registration successful for user: " + registeredUser.getUsername());
            } catch (HttpClientErrorException e) {
                Notification.show("Registration failed: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });

        registrationForm.add(usernameField, passwordField, registerButton);
        add(registrationForm);
    }*/

}

package es.uah.client.client.frontend;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route("subscriptions")
public class Subscriptions extends VerticalLayout {

    public Subscriptions() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar);

        // Add components to list subscriptions here

        Button backButton = new Button("Back to Dashboard", e -> getUI().ifPresent(ui -> ui.navigate("index")));
        add(backButton);
    }
}

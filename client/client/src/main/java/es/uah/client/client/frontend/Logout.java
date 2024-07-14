package es.uah.client.client.frontend;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route("logout")
public class Logout extends Div {

    public Logout() {
        VaadinSession.getCurrent().getSession().invalidate();
        VaadinSession.getCurrent().close();

        getUI().ifPresent(ui -> ui.navigate("index"));

    }
}
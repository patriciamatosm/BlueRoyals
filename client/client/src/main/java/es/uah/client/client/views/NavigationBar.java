package es.uah.client.client.views;


import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class NavigationBar extends HorizontalLayout {

    public NavigationBar() {
        getStyle().set("width", "100%");
        getStyle().set("background-color", "#007bff");
        getStyle().set("padding", "10px");
        getStyle().set("position", "fixed");
        getStyle().set("top", "0");
        getStyle().set("z-index", "1000");
        getStyle().set("box-shadow", "0 2px 4px rgba(0, 0, 0, 0.1)");

        Span logo = new Span("BlueRoyals");
        logo.getStyle().set("color", "white");
        logo.getStyle().set("font-weight", "bold");

        Anchor profileLink = new Anchor("profile", "Profile");
        profileLink.getStyle().set("color", "white");
        profileLink.getStyle().set("margin-left", "auto");

        Anchor subscriptionsLink = new Anchor("subscriptions", "My Subscriptions");
        subscriptionsLink.getStyle().set("color", "white");
        subscriptionsLink.getStyle().set("margin-left", "20px");

        Anchor createdEventsLink = new Anchor("events", "My Events");
        createdEventsLink.getStyle().set("color", "white");
        createdEventsLink.getStyle().set("margin-left", "20px");

        Anchor createEventLink = new Anchor("create-event", "Create Event");
        createEventLink.getStyle().set("color", "white");
        createEventLink.getStyle().set("margin-left", "20px");

        Anchor logoutLink = new Anchor("logout", "Logout");
        logoutLink.getStyle().set("color", "white");
        logoutLink.getStyle().set("margin-left", "20px");

        Div links = new Div(profileLink, subscriptionsLink, createdEventsLink, createEventLink, logoutLink);
        links.getStyle().set("margin-left", "auto");

        add(logo, links);
    }
}



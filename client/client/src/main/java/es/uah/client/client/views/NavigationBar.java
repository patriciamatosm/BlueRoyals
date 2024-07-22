package es.uah.client.client.views;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import es.uah.client.client.model.User;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class NavigationBar extends HorizontalLayout {

    public NavigationBar() {
        setWidthFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.BETWEEN);
        getStyle().set("background-color", "#007bff");
        getStyle().set("padding", "10px");

        // Application name and logo
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setAlignItems(Alignment.CENTER);
//        Image logo = new Image("path/to/logo.png", "BlueRoyals Logo");
//        logo.setHeight("40px");
        Span appName = new Span("BlueRoyals");
        appName.getStyle().set("color", "white");
        appName.getStyle().set("font-size", "24px");
        appName.getStyle().set("font-weight", "bold");
        logoLayout.add(appName);

        // Navigation buttons
        HorizontalLayout navButtons = new HorizontalLayout();
        navButtons.setAlignItems(Alignment.CENTER);

        // Home button
        Button homeButton = new Button("Home");
        homeButton.addClickListener(e -> UI.getCurrent().navigate("index"));
        homeButton.getStyle().set("color", "white");
        homeButton.getStyle().set("background-color", "transparent");
        homeButton.getStyle().set("border", "none");

        // Events dropdown
        MenuBar eventsMenu = new MenuBar();
        MenuItem eventsItem = eventsMenu.addItem("Events");
        eventsItem.getElement().getStyle().set("color", "white");
        SubMenu eventsSubMenu = eventsItem.getSubMenu();
        eventsSubMenu.addItem("My Events", e -> UI.getCurrent().navigate("events"));
        eventsSubMenu.addItem("My Subscriptions", e -> UI.getCurrent().navigate("subscriptions"));

        // User dropdown
        MenuBar userMenu = new MenuBar();
        VaadinSession session = VaadinSession.getCurrent();
        User user = (User) session.getAttribute(User.class);
        MenuItem userItem;
        if(user == null) {
            userItem = userMenu.addItem("User");
        } else {
            userItem = userMenu.addItem(user.getName());
        }
        userItem.getElement().getStyle().set("color", "white");
        SubMenu userSubMenu = userItem.getSubMenu();
        userSubMenu.addItem("Profile", e -> UI.getCurrent().navigate("profile"));
        userSubMenu.addItem("Log out", e -> {
//            VaadinSession.getCurrent().getSession().invalidate();
//            VaadinSession.getCurrent().close();
            UI.getCurrent().navigate("");
        });

        navButtons.add(homeButton, eventsMenu, userMenu);

        add(logoLayout, navButtons);
    }
}



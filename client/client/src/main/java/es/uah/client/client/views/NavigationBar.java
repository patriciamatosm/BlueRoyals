package es.uah.client.client.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.server.VaadinSession;
import es.uah.client.client.model.User;

public class NavigationBar extends HorizontalLayout {

    public NavigationBar() {
        setWidthFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.BETWEEN);

        getStyle().set("background-color", "#0044cc");  // Navy background
        getStyle().set("box-shadow", "0 4px 8px 0 rgba(0, 0, 0, 0.2)");
        getStyle().set("padding", "10px 20px");
        getStyle().set("justify-content", "space-between");

        // Application logo and name
        Div logoLayout = new Div();
        logoLayout.getStyle().set("display", "flex").set("align-items", "center");

        Span appName = new Span("BlueRoyals");
        appName.getStyle().set("color", "white").set("font-size", "24px").set("font-weight", "bold")
                .set("margin-left", "10px");

        logoLayout.add(appName);

        // Navigation buttons
        HorizontalLayout navButtons = new HorizontalLayout();
        navButtons.setAlignItems(Alignment.CENTER);

        Button homeButton = new Button("Home", e -> UI.getCurrent().navigate("index"));
        styleNavButton(homeButton);

        MenuBar eventsMenu = new MenuBar();
        eventsMenu.addThemeVariants();
        MenuItem events = eventsMenu.addItem("Events");
        styleNavButton(events);

        events.getSubMenu().addItem("My Events", e -> UI.getCurrent().navigate("events"));
        events.getSubMenu().addItem("My Subscriptions", e -> UI.getCurrent().navigate("subscriptions"));


//         User dropdown
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
            UI.getCurrent().navigate("");
        });

        navButtons.add(homeButton, eventsMenu, userMenu);

        add(logoLayout, navButtons);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.BETWEEN);
    }

    private void styleNavButton(Button button) {
        button.getStyle().set("color", "white");
        button.getElement().getStyle().set("background", "transparent");
        button.getElement().getStyle().set("border", "none");
    }

    private void styleNavButton(MenuItem menuItem) {
        menuItem.getElement().getStyle().set("color", "white");
    }
}

package es.uah.client.client.frontend;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NavigationBar extends HorizontalLayout {

    public NavigationBar() {
        setWidthFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "#E8E8E8");
        getStyle().set("color", "#007bff");
        getStyle().set("position", "fixed");
        getStyle().set("top", "0");
        getStyle().set("left", "0");
        getStyle().set("z-index", "1000");


        Image logo = new Image("images/blue_royals_logo.png", "BlueRoyals Logo");
        logo.setHeight("30px");
        logo.setAlt("BlueRoyals Logo");


        H1 title = new H1("BlueRoyals");
        title.getStyle().set("margin", "0");
        title.getStyle().set("font-size", "18px");
        title.getStyle().set("color", "#007bff");

        Div spacer = new Div();
        spacer.setWidthFull();

        HorizontalLayout logoAndTitle = new HorizontalLayout(logo, title);
        logoAndTitle.setAlignItems(Alignment.CENTER);

        add(logoAndTitle, spacer);
    }
}


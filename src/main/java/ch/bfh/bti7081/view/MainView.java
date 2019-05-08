package ch.bfh.bti7081.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * The main view contains a button and a click listener.
 */
@Route(value = "", layout = Layout.class)
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainView extends VerticalLayout {
    private String loremipsum = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";

    // title and welcometext
    private H1 title = new H1("Herzlich Willkommen!");
    private Label welcomeText = new Label(loremipsum);
    //private VerticalLayout layout = new VerticalLayout(title, welcomeText);

    // block "Wichtige Links"
    private H2 linksTitle = new H2("Wichtige Links");
    private Label linksText = new Label(loremipsum);
    private VerticalLayout linksLayout = new VerticalLayout(linksTitle, linksText);

    // block "Seminare"
    private VerticalLayout seminarsLayout = new VerticalLayout(new Label(loremipsum));

    // block "Hot Topics"
    private VerticalLayout hotTopicsLayout = new VerticalLayout(new Label(loremipsum));

    private HorizontalLayout siteFeatures = new HorizontalLayout(linksLayout, seminarsLayout, hotTopicsLayout);

    public MainView() {
        //this.setWidth();
        this.add(title);
        this.add(welcomeText);
        //this.add(layout);
        this.add(siteFeatures);
    }
}

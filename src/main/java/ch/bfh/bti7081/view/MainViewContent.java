package ch.bfh.bti7081.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MainViewContent extends VerticalLayout {
    private String loremipsum = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";

    public MainViewContent() {
        // title and welcometext
        this.add(new H1("Herzlich Willkommen!"));
        this.add(new Label(loremipsum));
    }

    public void setFeatureView(VerticalLayout seminariesLayout) {
        // block "Wichtige Links"
        H2 linksTitle = new H2("Wichtige Links");
        Label linksText = new Label(loremipsum);
        VerticalLayout linksLayout = new VerticalLayout(linksTitle, linksText);

        // block "Hot Topics"
         VerticalLayout hotTopicsLayout = new VerticalLayout(new Label(loremipsum));

        // combine the feature-views and add them to site
        HorizontalLayout siteFeatures = new HorizontalLayout(linksLayout, seminariesLayout, hotTopicsLayout);
        this.add(siteFeatures);
    }
}

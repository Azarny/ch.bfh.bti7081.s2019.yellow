package ch.bfh.bti7081.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class MainViewContent extends VerticalLayout {
    private String loremipsum = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, " +
            "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, " +
            "sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. " +
            "Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";

    public MainViewContent() {
        // title and welcometext
        H1 title = new H1("Herzlich Willkommen!");
        Label welcomeText = new Label(loremipsum);
        this.add(title);
        this.add(welcomeText);
    }

    public void setFeatureView(List<String> seminaries) {
        // block "Wichtige Links"
        H2 linksTitle = new H2("Wichtige Links");
        Label linksText = new Label(loremipsum);
        VerticalLayout linksLayout = new VerticalLayout(linksTitle, linksText);

        // block "Hot Topics"
        VerticalLayout hotTopicsLayout = new VerticalLayout(new Label(loremipsum));

        // block "nächste Seminare"
        VerticalLayout seminariesLayout = new VerticalLayout();
        seminariesLayout.add(new H2("Nächste Seminare"));
        for (String seminar : seminaries) {
            seminariesLayout.add(new Label(seminar));
        }

        // combine the feature-views and add them to site
        HorizontalLayout siteFeatures = new HorizontalLayout(linksLayout, seminariesLayout, hotTopicsLayout);
        this.add(siteFeatures);
    }
}

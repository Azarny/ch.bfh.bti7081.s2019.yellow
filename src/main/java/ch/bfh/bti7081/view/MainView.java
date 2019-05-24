package ch.bfh.bti7081.view;

import ch.bfh.bti7081.presenter.MainViewPresenter;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@Route(value = "", layout = Layout.class)
public class MainView extends VerticalLayout {
    @Autowired
    private MainViewPresenter mainViewPresenter;

    private String loremipsum = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, " +
            "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, " +
            "sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. " +
            "Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";

    // title and welcometext
    private H1 title = new H1("Herzlich Willkommen!");
    private Label welcomeText = new Label(loremipsum);
    private VerticalLayout welcomeLayout = new VerticalLayout(title, welcomeText);

    // block "Wichtige Links"
    private H2 linksTitle = new H2("Wichtige Links");
    private Label linksText = new Label(loremipsum);
    private VerticalLayout linksLayout = new VerticalLayout(linksTitle, linksText);

    // block "Hot Topics"
    private H2 hotTopicsTitle = new H2("Hot Topics");
    private VerticalLayout hotTopicsLayout = new VerticalLayout(hotTopicsTitle);

    // block "nächste Seminare"
    private H2 seminariesTitle = new H2("Nächste Seminare");
    private VerticalLayout seminariesLayout = new VerticalLayout(seminariesTitle);

    // container for the feature views
    private HorizontalLayout siteFeatures = new HorizontalLayout();

    public MainView() {

    }

    @PostConstruct
    public void init() {
        // mainViewPresenter = new MainViewPresenter();

        // title and welcometext
        this.add(welcomeLayout);

        List<String> seminaries = mainViewPresenter.getNextSeminaries();
        for (String seminar : seminaries) {
            seminariesLayout.add(new Label(seminar));
        }
        siteFeatures.add(linksLayout);
        siteFeatures.add(hotTopicsLayout);
        siteFeatures.add(seminariesLayout);
        this.add(siteFeatures);
    }
}

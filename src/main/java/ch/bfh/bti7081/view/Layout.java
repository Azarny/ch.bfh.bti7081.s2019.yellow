package ch.bfh.bti7081.view;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

public class Layout extends VerticalLayout implements RouterLayout {
    private final HorizontalLayout menuBar = new HorizontalLayout(
            new RouterLink("Startseite", MainViewContainer.class),
            new RouterLink("Seminare", SeminarView.class),
            new RouterLink("FaQ", FaqView.class),
            new RouterLink("Forum", ForumView.class)
    );

    public Layout() {
        this.add(menuBar);
    }
}

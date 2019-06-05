package ch.bfh.bti7081.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "faq", layout = Layout.class)
public class FaqView extends VerticalLayout {
    private H1 title = new H1("FaQ");

    public FaqView() {
        this.add(title);
    }
}

package ch.bfh.bti7081.view;

import ch.bfh.bti7081.presenter.NewSeminarPresenter;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "seminar/new", layout = Layout.class)
public class NewSeminarView extends VerticalLayout {

    public NewSeminarView() {
        NewSeminarViewImpl view = new NewSeminarViewImpl();
        NewSeminarPresenter presenter = new NewSeminarPresenter(view);
        view.setPresenter(presenter);

        add(view);
    }
}

package ch.bfh.bti7081.view;

import ch.bfh.bti7081.presenter.MainViewPresenter;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = Layout.class)
public class MainViewContainer extends VerticalLayout {
    public MainViewContainer() {
        MainViewContent content = new MainViewContent();
        new MainViewPresenter(content);
        this.add(content);
    }
}
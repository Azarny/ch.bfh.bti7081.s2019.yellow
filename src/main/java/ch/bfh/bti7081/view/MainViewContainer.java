package ch.bfh.bti7081.view;

import ch.bfh.bti7081.presenter.MainViewPresenter;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Route(value = "", layout = Layout.class)
@Component
@UIScope
@Tag("home-view")
public class MainViewContainer extends VerticalLayout {
    public MainViewContainer() {
        MainViewContent content = new MainViewContent();
        new MainViewPresenter(content);
        this.add(content);
    }
}
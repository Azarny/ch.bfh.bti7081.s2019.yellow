package ch.bfh.bti7081.view;

import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.presenter.MainViewPresenter;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = Layout.class)
//@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainViewContainer extends VerticalLayout {
    public MainViewContainer() {
        MainViewContent content = new MainViewContent();
        SeminarManager manager = new SeminarManager();
        new MainViewPresenter(content, manager);
        this.add(content);
    }
}
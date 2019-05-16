package ch.bfh.bti7081.view;

import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.presenter.SeminarPresenter;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "seminar", layout = Layout.class)
public class SeminarView extends VerticalLayout {
  
  public SeminarView(){
    SeminarViewImpl view = new SeminarViewImpl();
    SeminarManager manager = new SeminarManager();
    new SeminarPresenter(manager,view);
    add(view);
  }
}

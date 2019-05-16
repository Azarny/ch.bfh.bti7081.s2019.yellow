package ch.bfh.bti7081.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "seminar", layout = Layout.class)
public class SeminarView extends VerticalLayout {
    private H1 title = new H1("Seminarfinder");
    private Button newSeminar = new Button("Neues Seminar", new Icon(VaadinIcon.EDIT));
    public SeminarView(){
        this.add(title,newSeminar);
        newSeminar.addClickListener(event -> {
            newSeminar.getUI().ifPresent(ui -> ui.navigate("newSeminar"));
        });
    }
}

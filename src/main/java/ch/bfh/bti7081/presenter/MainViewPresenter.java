package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.view.MainViewContent;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainViewPresenter {
    private MainViewContent view;
    private SeminarManager seminarManager;

    public MainViewPresenter(MainViewContent view, SeminarManager manager) {
        this.view = view;
        this.seminarManager = manager;

        // generate next seminars on startup
        this.view.setSeminarsLayout(this.getNextSeminaries());
    }

    private VerticalLayout getNextSeminaries() {
        VerticalLayout nextSeminars = new VerticalLayout(new H2("Nächste Seminare"));
        List<Seminar> seminaries = SeminarManager.getSeminaries();
        seminaries.sort((s1, s2) -> s1.getDate().compareTo(s2.getDate()));

        // get n first seminaries and display them
        int seminarCount = 5;
        if (seminarCount > seminaries.size()) {
            seminarCount = seminaries.size();
        }
        for (int i = 0; i < seminarCount; i++) {
            nextSeminars.add(new Label(
                    seminaries.get(i).getDate().format(DateTimeFormatter.BASIC_ISO_DATE) + " - " +
                            seminaries.get(i).getLocation() + " - " +
                            seminaries.get(i).getTitle()
            ));
        }
        //TODO
        return nextSeminars;
    }
}

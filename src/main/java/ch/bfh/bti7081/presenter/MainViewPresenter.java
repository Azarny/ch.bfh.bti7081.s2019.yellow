package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.view.MainViewContent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainViewPresenter {
    public MainViewPresenter(MainViewContent view) {
        // generate next seminars on startup
        view.setFeatureView(getNextSeminaries());
    }

    /**
     * creates a VerticalLayout with a title and the next few seminaries to display on the homepage
     *
     * @return VerticalLayout to add to view
     */
    private static List<String> getNextSeminaries() {
        List<String> nextSeminars = new ArrayList<>();
        List<Seminar> seminaries = SeminarManager.getSeminaries();
        seminaries.sort(Comparator.comparing(Seminar::getDate));
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");

        // get next few seminaries
        long nextSeminariesCount = 5;
        seminaries = seminaries.stream()
                .filter(s -> s.getDate().compareTo(now) > 0)
                .limit(nextSeminariesCount)
                .collect(Collectors.toList());

        // collect the necessary information from the seminaries and prepare them for display
        for (Seminar seminar : seminaries) {
            nextSeminars.add(
                    seminar.getDate().format(dateTimeFormatter) + " - " +
                            seminar.getTitle() + " (" + seminar.getLocation() + ")"
            );
        }

        return nextSeminars;
    }
}

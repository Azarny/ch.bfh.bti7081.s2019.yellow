package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.model.ValidationConstants;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SeminarManager {
    // manages the communication between backend and frontend
    // the list of methods isn't completed yet, just some sample methods for the class diagramm

    public List<Seminar> getSeminaries() {
        return mockSeminaries();
    }

    public List<Seminar> getFilteredSeminars(SeminarFilter filter) {
        return getSeminaries().stream()
                //filter category
                .filter(seminar -> {
                    if (filter.getCategory() == null) {
                        return true;
                    } else {
                        return seminar.getCategory().getName().toLowerCase().equals(filter.getCategory()
                                .getName().toLowerCase());
                    }
                })
                //filter location
                .filter(seminar -> {
                    if (filter.getLocation() == null) {
                        return true;
                    } else {
                        return seminar.getLocation().toLowerCase().contains(filter.getLocation().toLowerCase());
                    }
                })
                //filter date
                .filter(seminar -> {
                    if (filter.getToDate() == null) {
                        return true;
                    } else {
                        return seminar.getDate().isBefore(filter.getToDate().atStartOfDay().plusDays(1)
                                .minusSeconds(1));
                    }
                })
                .filter(seminar -> {
                    if (filter.getFromDate() == null) {
                        return true;
                    } else {
                        return seminar.getDate().isAfter(filter.getFromDate().atStartOfDay());
                    }
                })
                .filter(seminar -> {
                    if (filter.getKeyword() == null) {
                        return true;
                    } else {
                        String[] keywords = filter.getKeyword().split(" ");
                        for (String keyword : keywords) {
                            if (seminar.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                                return true;
                            }
                            if (seminar.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                                return true;
                            }
                            if (seminar.getStreet().toLowerCase().contains(keyword.toLowerCase())) {
                                return true;
                            }
                            if (seminar.getUrl().toLowerCase().contains(keyword.toLowerCase())) {
                                return true;
                            }
                            if (seminar.getPlz().toString().contains(keyword)) {
                                return true;
                            }
                        }
                        return false;
                    }
                })
                .collect(Collectors.toList());

    }

    public Seminar createSeminar(Seminar seminar) {
        throw new IllegalArgumentException("Not implemented yet.");
    }

    public String validateSeminar(Seminar seminar) {
        String returnString = "";

        if (seminar.getStreet() == null || seminar.getStreet().trim().length() <
                ValidationConstants.MIN_STREET_LENGTH.value) {
            returnString += "no valid street name, ";
        }

        if (seminar.getHouseNumber() == null || !seminar.getHouseNumber().matches("^\\d*\\w$") ||
                seminar.getHouseNumber().trim().length() <
                        ValidationConstants.MIN_STREETNUMBER_LENGTH.value) {
            returnString += "no valid house number, ";
        }

        if (seminar.getPlz() == null || !((seminar.getPlz() > 999 && seminar.getPlz() < 10000) ||
                (seminar.getPlz() > 99999 && seminar.getPlz() < 1000000))) {
            returnString += "no valid PLZ, ";
        }

        if (seminar.getLocation() == null || seminar.getLocation().trim().length() <
                ValidationConstants.MIN_LOCATION_LENGTH.value) {
            returnString += "no valid location, ";
        }

        if (seminar.getTitle() == null || seminar.getTitle().trim().length() <
                ValidationConstants.MIN_TITLE_LENGTH.value) {
            returnString += "no valid title, ";
        }

        if (seminar.getDate() == null || seminar.getDate().isBefore(LocalDateTime.now()) || seminar.getDate()
                .isAfter(LocalDateTime.now().plusYears(ValidationConstants.MAX_YEARS_IN_FUTURE.value))) {
            returnString += "no valid date, ";
        }

        if (seminar.getCategory() == null) {
            returnString += "no valid category, ";
        }

        //regex pattern description:
        //^((https?|ftp)://)? --> allows http://, https:// and nothing
        // (\w+\.)+(\w{2}|\w{3}) --> allows url with one or more "parts" before the .topleveldomain.
        // top level domains are made of 2 or 3 chars
        // (/\S+(\./\S+)*)?$ --> allows all the stuff after the last / but no whitespaces
        if (seminar.getUrl() == null || !seminar.getUrl().
                matches("^((https?)://)?(\\w+\\.)+(\\w{2}|\\w{3})(/\\S+(\\./\\S+)*)?$")) {
            returnString += "no valid URL, ";
        }

        if (seminar.getDescription() == null || seminar.getDescription().trim().length() <
                ValidationConstants.MIN_DESCRIPTION_LENGTH.value) {
            returnString += "no valid description, ";
        }

        return returnString;
    }

    private List<Seminar> mockSeminaries() {
        //These is mock data, nothing here is productive code.
        List<Seminar> mockSeminaries = new ArrayList<>();
        SeminarCategoryManager categoryManager = new SeminarCategoryManager();

        Seminar seminarMock1 = new Seminar();
        seminarMock1.setCategory(categoryManager.getSeminarCategories().get(2));
        seminarMock1.setDate(dateGenerator("2019-11-07 13:30"));
        seminarMock1.setDescription("Ärzte berichten von ihren Erlebnissen mit der Krankheit. " +
                "Dr. Luis Alvador gibt Ihnen einen Überblick über die medizinischen Informationen, " +
                "eine Podiumsdiskussion mit Angehörigen findet statt.");
        seminarMock1.setHouseNumber("102");
        seminarMock1.setUrl("https://vaadin.com");
        seminarMock1.setLocation("Bern");
        seminarMock1.setTitle("Wie unterstütze ich einen Angehörigen?");
        seminarMock1.setStreet("Wankdorffeldstrasse");
        seminarMock1.setPlz(3014);
        mockSeminaries.add(seminarMock1);

        Seminar seminarMock2 = new Seminar();
        seminarMock2.setCategory(categoryManager.getSeminarCategories().get(0));
        seminarMock2.setDate(dateGenerator("2019-08-07 10:30"));
        seminarMock2.setDescription("Betroffene erzählen von Ihren Problemen im Umgang mit der Krankheit " +
                "und zeigen wie Sie diese tagtäglich überwinden.");
        seminarMock2.setHouseNumber("5");
        seminarMock2.setUrl("https://www.nzz.ch/");
        seminarMock2.setLocation("Bern");
        seminarMock2.setTitle("Sozialphobie - Mit der Angst umgehen");
        seminarMock2.setStreet(" Schanzenstrasse");
        seminarMock2.setPlz(3008);
        mockSeminaries.add(seminarMock2);

        Seminar seminarMock3 = new Seminar();
        seminarMock3.setCategory(categoryManager.getSeminarCategories().get(1));
        seminarMock3.setDate(dateGenerator("2019-09-09 20:00"));
        seminarMock3.setDescription("Wir haben zehn Angehörige von Personen mit Sozialphobie eingeladen, " +
                "die Ihnen von Ihren Erlebnissen erzählen. Treffen Sie uns zu einem Feierabendbier. " +
                "Mehr unter dem Link. ");
        seminarMock3.setHouseNumber("18");
        seminarMock3.setUrl("https://www.zeit.de/index");
        seminarMock3.setLocation("Rothrist");
        seminarMock3.setTitle("Angehörigentreffen");
        seminarMock3.setStreet("Bachweg");
        seminarMock3.setPlz(4852);
        mockSeminaries.add(seminarMock3);

        Seminar seminarMock4 = new Seminar();
        seminarMock4.setCategory(categoryManager.getSeminarCategories().get(0));
        seminarMock4.setDate(dateGenerator("2020-02-15 09:00"));
        seminarMock4.setDescription("Ein Treffen mit anderen Betroffenen. Hier können Sie sich entspannt " +
                "fühlen, niemand wird sie verurteilen, da wir dasselbe tagtäglich auch durchmachen.");
        seminarMock4.setHouseNumber("420");
        seminarMock4.setUrl("https://de.wikipedia.org/wiki/Soziale_Phobie");
        seminarMock4.setLocation("Zürich");
        seminarMock4.setTitle("Betroffenenhöck");
        seminarMock4.setStreet("Badenerstrasse");
        seminarMock4.setPlz(8040);
        mockSeminaries.add(seminarMock4);

        return mockSeminaries;
    }

    private LocalDateTime dateGenerator(String timeToParse) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(timeToParse, formatter);
    }

    public void deleteSeminar(Integer id) {
        throw new IllegalArgumentException("Not implemented yet.");
    }


}

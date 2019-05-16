package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.model.seminar.Seminar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeminarManager {
    // manages the communication between backend and frontend
    // the list of methods isn't completed yet, just some sample methods for the class diagramm

    // some variables for the validation, used in SeminarManager.java and Seminar.java
    public static int maxYearsInFuture = 5;
    public static int minTitleLength = 4;
    public static int minDescriptionLength = 10;
    public static int minStreetLength = 2;
    public static int minStreetNumberLength = 1;
    public static int minLocationLength = 2;


    public static List<Seminar> getSeminaries() {
        return mockSeminaries();
    }

    public List<Seminar> getFilteredSeminars(Map<String, String> filters) {
        throw new IllegalArgumentException("Not implemented yet.");
    }

    public static Seminar createSeminar(Seminar seminar) {
        throw new IllegalArgumentException("Not implemented yet.");
    }

    public void deleteSeminar(Integer id) {
        throw new IllegalArgumentException("Not implemented yet.");
    }

    public static String validateSeminar(Seminar seminar) {
        String returnString = "";

        if (seminar.getStreet() == null || seminar.getStreet().trim().length() < SeminarManager.minStreetLength) {
            returnString += "no valid street name, ";
        }

        if (seminar.getHouseNumber() == null || !seminar.getHouseNumber().matches("^\\d*\\w$") || seminar.getHouseNumber().trim().length() < SeminarManager.minStreetNumberLength) {
            returnString += "no valid house number, ";
        }

        if (seminar.getPlz() == null || !((seminar.getPlz() > 999 && seminar.getPlz() < 10000) || (seminar.getPlz() > 99999 && seminar.getPlz() < 1000000))) {
            returnString += "no valid PLZ, ";
        }

        if (seminar.getLocation() == null || seminar.getLocation().trim().length() < SeminarManager.minLocationLength) {
            returnString += "no valid location, ";
        }

        if (seminar.getTitle() == null || seminar.getTitle().trim().length() < SeminarManager.minTitleLength) {
            returnString += "no valid title, ";
        }

        if (seminar.getDate() == null || seminar.getDate().isBefore(LocalDateTime.now()) || seminar.getDate().isAfter(LocalDateTime.now().plusYears(SeminarManager.maxYearsInFuture))) {
            returnString += "no valid date, ";
        }

        if (seminar.getCategory() == null) {
            returnString += "no valid category, ";
        }

        //regex pattern description:
        //^((https?|ftp)://)? --> allows http://, https:// and nothing
        // (\w+\.)+(\w{2}|\w{3}) --> allows url with one or more "parts" before the .topleveldomain. top level domains are made of 2 or 3 chars
        // (/\S+(\./\S+)*)?$ --> allows all the stuff after the last / but no whitespaces
        if (seminar.getUrl() == null || !seminar.getUrl().matches("^((https?)://)?(\\w+\\.)+(\\w{2}|\\w{3})(/\\S+(\\./\\S+)*)?$")) {
            returnString += "no valid URL, ";
        }

        if (seminar.getDescription() == null || seminar.getDescription().trim().length() < SeminarManager.minDescriptionLength) {
            returnString += "no valid description, ";
        }

        return returnString;
    }

    private static List<Seminar> mockSeminaries() {
        //These is mock data, nothing here is productive code.
        List<Seminar> mockSeminaries = new ArrayList<>();
        Seminar seminarMock1 = new Seminar();
        seminarMock1.setCategory(SeminarCategoryManager.getSeminarCategories().get(2));
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
        seminarMock2.setCategory(SeminarCategoryManager.getSeminarCategories().get(0));
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
        seminarMock3.setCategory(SeminarCategoryManager.getSeminarCategories().get(1));
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
        seminarMock4.setCategory(SeminarCategoryManager.getSeminarCategories().get(0));
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

    private static LocalDateTime dateGenerator(String timeToParse) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(timeToParse, formatter);
    }


}

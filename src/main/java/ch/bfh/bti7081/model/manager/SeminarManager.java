package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarFilter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SeminarManager {
    // manages the communication between backend and frontend
    // the list of methods isn't completed yet, just some sample methods for the class diagramm

    public static List<Seminar> getSeminaries() {
        return mockSeminaries();
    }

    public static List<Seminar> getFilteredSeminars(SeminarFilter filter) {
        return getSeminaries().stream().
                filter(seminar -> {
                    if (filter.getCategory() != null)
                        return seminar.getCategory().getName().equals(filter.getCategory().getName());
                    else
                        return true;
                }).collect(Collectors.toList());

    }

    public Seminar createSeminar(Seminar seminar) {
        throw new IllegalArgumentException("Not implemented yet.");
    }

    public void deleteSeminar(Integer id) {
        throw new IllegalArgumentException("Not implemented yet.");
    }

    private static List<Seminar> mockSeminaries(){
        //These is mock data, nothing here is productive code.
        List<Seminar> mockSeminaries = new ArrayList<>();
        Seminar seminarMock1=new Seminar();
        seminarMock1.setCategory(SeminarCategoryManager.getSeminarCategories().get(2));
        seminarMock1.setDate(dateGenerator("2019-11-07 13:30"));
        seminarMock1.setDescription("Ärzte berichten von ihren Erlebnissen mit der Krankheit. " +
                "Dr. Luis Alvador gibt Ihnen einen Überblick über die medizinischen Informationen, " +
                "eine Podiumsdiskussion mit Angehörigen findet statt.");
        seminarMock1.setHouseNumber("102");
        seminarMock1.setLink("https://vaadin.com");
        seminarMock1.setLocation("Bern");
        seminarMock1.setTitle("Wie unterstütze ich einen Angehörigen?");
        seminarMock1.setStreet("Wankdorffeldstrasse");
        seminarMock1.setPlz(3014);
        mockSeminaries.add(seminarMock1);

        Seminar seminarMock2=new Seminar();
        seminarMock2.setCategory(SeminarCategoryManager.getSeminarCategories().get(0));
        seminarMock2.setDate(dateGenerator("2019-08-07 10:30"));
        seminarMock2.setDescription("Betroffene erzählen von Ihren Problemen im Umgang mit der Krankheit " +
                "und zeigen wie Sie diese tagtäglich überwinden.");
        seminarMock2.setHouseNumber("5");
        seminarMock2.setLink("https://www.nzz.ch/");
        seminarMock2.setLocation("Bern");
        seminarMock2.setTitle("Sozialphobie - Mit der Angst umgehen");
        seminarMock2.setStreet(" Schanzenstrasse");
        seminarMock2.setPlz(3008);
        mockSeminaries.add(seminarMock2);

        Seminar seminarMock3=new Seminar();
        seminarMock3.setCategory(SeminarCategoryManager.getSeminarCategories().get(1));
        seminarMock3.setDate(dateGenerator("2019-09-09 20:00"));
        seminarMock3.setDescription("Wir haben zehn Angehörige von Personen mit Sozialphobie eingeladen, " +
                "die Ihnen von Ihren Erlebnissen erzählen. Treffen Sie uns zu einem Feierabendbier. " +
                "Mehr unter dem Link. ");
        seminarMock3.setHouseNumber("18");
        seminarMock3.setLink("https://www.zeit.de/index");
        seminarMock3.setLocation("Rothrist");
        seminarMock3.setTitle("Angehörigentreffen");
        seminarMock3.setStreet("Bachweg");
        seminarMock3.setPlz(4852);
        mockSeminaries.add(seminarMock3);

        Seminar seminarMock4=new Seminar();
        seminarMock4.setCategory(SeminarCategoryManager.getSeminarCategories().get(0));
        seminarMock4.setDate(dateGenerator("2020-02-15 09:00"));
        seminarMock4.setDescription("Ein Treffen mit anderen Betroffenen. Hier können Sie sich entspannt " +
                "fühlen, niemand wird sie verurteilen, da wir dasselbe tagtäglich auch durchmachen.");
        seminarMock4.setHouseNumber("420");
        seminarMock4.setLink("https://de.wikipedia.org/wiki/Soziale_Phobie");
        seminarMock4.setLocation("Zürich");
        seminarMock4.setTitle("Betroffenenhöck");
        seminarMock4.setStreet("Badenerstrasse");
        seminarMock4.setPlz(8040);
        mockSeminaries.add(seminarMock4);

        return mockSeminaries;
    }

    private static LocalDateTime dateGenerator(String timeToParse){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(timeToParse, formatter);
    }



}

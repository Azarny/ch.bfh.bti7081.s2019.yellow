package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.model.seminar.Seminar;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Map;

public class SeminarManager {
    // manages the communication between backend and frontend
    // the list of methods isn't completed yet, just some sample methods for the class diagramm

    public List<Seminar> getSeminars() {
        throw new NotImplementedException();
    }

    public List<Seminar> getFilteredSeminars(Map<String, String> filters) {
        throw new NotImplementedException();
    }

    public Seminar createSeminar(Seminar seminar) {
        throw new NotImplementedException();
    }

    public void deleteSeminar(Integer id) {
        throw new NotImplementedException();
    }
}

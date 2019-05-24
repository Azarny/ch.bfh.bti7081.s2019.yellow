package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.model.faq.FaqEntry;

import java.util.List;

public class FaqManager {
    // manages the communication between backend and frontend
    // the list of methods isn't completed yet, just some sample methods for the class diagramm

    public List<FaqEntry> getAllFaqEntries() {
        throw new IllegalArgumentException("Not implemented yet.");
    }

    public List<FaqEntry> getFilteredFaqEntries(String titleFilter) {
        throw new IllegalArgumentException("Not implemented yet.");
    }

    public FaqEntry createFaqEntry(FaqEntry entry) {
        throw new IllegalArgumentException("Not implemented yet.");
    }

    public void deleteFaqEntry(Integer id) {
        throw new IllegalArgumentException("Not implemented yet.");
    }
}

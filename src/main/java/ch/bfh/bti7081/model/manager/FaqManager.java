package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.model.faq.FaqEntry;
import ch.bfh.bti7081.model.repositories.FaqEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author siegn2
 */
public class FaqManager {
    @Autowired
    FaqEntryRepository faqEntryRepository;

    public List<FaqEntry> getAllFaqEntries() {
        throw new IllegalArgumentException("Not implemented yet.");
    }

    public List<FaqEntry> getFilteredFaqEntries(String titleFilter) {
        throw new IllegalArgumentException("Not implemented yet.");
    }

    public void createFaqEntry(FaqEntry entry) {
        faqEntryRepository.save(entry);
    }

    public void deleteFaqEntry(Integer id) {
        throw new IllegalArgumentException("Not implemented yet.");
    }
}

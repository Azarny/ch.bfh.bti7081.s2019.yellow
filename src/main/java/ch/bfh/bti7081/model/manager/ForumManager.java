package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.presenter.dto.ForumEntryDTO;
import ch.bfh.bti7081.model.forum.ForumCategory;
import ch.bfh.bti7081.model.forum.ForumEntry;
import ch.bfh.bti7081.model.repositories.ForumEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ForumManager {
    // manages the communication between backend and frontend
    // the list of methods isn't completed yet, just some sample methods for the class diagramm

    @Autowired
    private ForumEntryRepository forumEntryRepository;

    public List<ForumEntry> getAllEntriesByCategory(ForumCategory category) {
        throw new IllegalArgumentException("Not implemented yet.");
    }

    public ForumEntryDTO getForumEntry(Integer id) {
        throw new IllegalArgumentException("Not implemented yet.");
    }

    public void createForumEntry(ForumEntry forumEntry) {
        forumEntryRepository.save(forumEntry);
    }

    public void deleteForumEntry(Integer id) throws Exception {
        throw new IllegalArgumentException("Not implemented yet.");
    }

}

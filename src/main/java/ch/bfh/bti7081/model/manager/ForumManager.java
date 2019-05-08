package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.model.dto.ForumEntryDTO;
import ch.bfh.bti7081.model.forum.ForumCategory;
import ch.bfh.bti7081.model.forum.ForumEntry;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class ForumManager {
    // manages the communication between backend and frontend
    // the list of methods isn't completed yet, just some sample methods for the class diagramm

    public List<ForumEntry> getAllEntriesByCategory(ForumCategory category) {
        throw new NotImplementedException();
    }

    public ForumEntryDTO getForumEntry(Integer id) {
        throw new NotImplementedException();
    }

    public ForumEntryDTO createForumEntry(ForumEntryDTO forumEntryDTO) {
        throw new NotImplementedException();
    }

    public void deleteForumEntry(Integer id) {
        throw new NotImplementedException();
    }

}

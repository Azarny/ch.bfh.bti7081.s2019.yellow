package domain;

import model.WikiEntry;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class WikiManager {
    // manages the communication between backend and frontend

    public List<WikiEntry> getAllWikiEntries(){
        throw new NotImplementedException();
    }
    public List<WikiEntry> getFilteredWikiEntries(String titleFilter){
        throw new NotImplementedException();
    }
}

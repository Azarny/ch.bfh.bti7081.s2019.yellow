package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.model.repositories.SeminarCategoryRepository;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SeminarCategoryManager {

    @Autowired
    SeminarCategoryRepository seminarCategoryRepository;

    public List<SeminarCategory> getSeminarCategories() {
        // we get here all categories, since we see no point in limiting the selection on the UI
        return seminarCategoryRepository.findAll();
    }

    /*
     * Author: siegn2
     */
    public SeminarCategory getSeminarCategoryByName(String name) throws Exception {
        SeminarCategory category = seminarCategoryRepository.findByName(name);
        if (category != null) {
            return category;
        } else {
            throw new Exception("Category does not exist.");
        }
    }
}

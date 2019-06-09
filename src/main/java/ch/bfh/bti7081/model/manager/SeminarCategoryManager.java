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

    /**
     * Get all SeminarCategories from the DB
     * @return List of SeminarCategories
     * @author siegn2
     */
    public List<SeminarCategory> getSeminarCategories() {
        // we get here all categories, since there is no possibility to add categories,
        // therefore we have full control over them
        return seminarCategoryRepository.findAll();
    }

    /**
     * Searches for SeminarCategory by name in the DB
     *
     * @param name SeminarCategory-name
     * @return SeminarCategory
     * @throws IllegalArgumentException Thrown if the category doesn't exist
     * @author siegn2
     */
    public SeminarCategory getSeminarCategoryByName(String name) throws IllegalArgumentException {
        SeminarCategory category = seminarCategoryRepository.findByName(name);
        if (category != null) {
            return category;
        } else {
            throw new IllegalArgumentException("Die angegebene Kategorie existiert nicht");
        }
    }
}

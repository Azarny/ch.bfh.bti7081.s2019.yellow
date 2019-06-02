package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.model.repositories.SeminarCategoryRepository;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SeminarCategoryManager {

    @Autowired
    SeminarCategoryRepository seminarCategoryRepository;

    public List<SeminarCategory> getSeminarCategories() {
        return seminarCategoryRepository.findAll();
    }

    public SeminarCategory getSeminarByName(String name) throws NoSuchFieldException {
        Optional<SeminarCategory> categories = getSeminarCategories().stream()
                .filter(s -> s.getName().equals(name)).findFirst();
        if (categories.isPresent()) {
            return categories.get();
        } else {
            throw new NoSuchFieldException("Category does not exist.");
        }
    }
}

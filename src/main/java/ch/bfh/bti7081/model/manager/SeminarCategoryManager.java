package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.model.seminar.SeminarCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeminarCategoryManager {
    public static List<SeminarCategory> getSeminarCategories() {
        return mockCategories();
    }

    public static SeminarCategory getSeminarByName(String name) throws Exception {
        Optional<SeminarCategory> categories = getSeminarCategories().stream().filter(s -> s.getName().equals(name)).findFirst();
        if (categories.isPresent()) {
            return categories.get();
        } else {
            throw new Exception("Category does not exist.");
        }
    }

    private static List<SeminarCategory> mockCategories() {
        //These is mock data, nothing here is productive code.
        List<SeminarCategory> mockSeminaryCategories = new ArrayList<>();
        mockSeminaryCategories.add(new SeminarCategory("Betroffene helfen Betroffenen"));
        mockSeminaryCategories.add(new SeminarCategory("Angehörige erzählen"));
        mockSeminaryCategories.add(new SeminarCategory("Ärzte geben Auskunft"));
        mockSeminaryCategories.add(new SeminarCategory("Allgemeines"));
        return mockSeminaryCategories;
    }
}

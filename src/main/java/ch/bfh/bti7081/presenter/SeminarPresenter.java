package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
import ch.bfh.bti7081.view.SeminarView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeminarPresenter {
    @Autowired
    private SeminarCategoryManager seminarCategoryManager;
    @Autowired
    private SeminarManager seminarManager;

    public List<SeminarCategory> getSeminarCategories() {
        return seminarCategoryManager.getSeminarCategories();
    }

    public List<Seminar> getSeminaries() {
        return seminarManager.getSeminaries();
    }

    public List<Seminar> getFilteredSeminaries(SeminarFilter seminarFilter) {
        return seminarManager.getFilteredSeminars(seminarFilter);
    }

    public SeminarPresenter() {
        //this.manager = seminarManager;
        //this.seminarCategoryManager = seminarCategoryManager;
        //this.view = view;
        //this.view.setSeminarCategoryList(seminarCategoryManager.getSeminarCategories());
        //TODO setSeminarList with getFilteredSeminaries-method (Manager-Class)
        //this.view.setSeminarList(seminarManager.getSeminaries());
    }
}

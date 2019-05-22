package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.view.SeminarViewImpl;

public class SeminarPresenter {

    private SeminarCategoryManager seminarCategoryManager;
    private SeminarViewImpl view;
    private SeminarManager manager;

    public SeminarPresenter(SeminarManager seminarManager, SeminarCategoryManager seminarCategoryManager, SeminarViewImpl view) {
        this.manager = seminarManager;
        this.seminarCategoryManager = seminarCategoryManager;
        this.view = view;
        this.view.setSeminarCategoryList(seminarCategoryManager.getSeminarCategories());
        //TODO setSeminarList with getFilteredSeminaries-method (Manager-Class)
        this.view.setSeminarList(seminarManager.getSeminaries());
    }
}

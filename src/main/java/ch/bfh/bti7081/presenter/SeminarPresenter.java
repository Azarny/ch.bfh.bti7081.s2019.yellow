package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.view.SeminarViewImpl;

public class SeminarPresenter {

    private SeminarViewImpl view;
    private SeminarManager manager;

    public SeminarPresenter(SeminarManager manager, SeminarViewImpl view) {
        this.manager = manager;
        this.view = view;
        this.view.setSeminarCategoryList(SeminarCategoryManager.getSeminarCategories());
        //TODO setSeminarList with getFilteredSeminaries-method (Manager-Class)
        this.view.setSeminarList(SeminarManager.getSeminaries());
    }
}

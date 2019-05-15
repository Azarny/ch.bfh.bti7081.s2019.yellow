package ch.bfh.bti7081.presenter;


import ch.bfh.bti7081.model.dto.NewSeminarDTO;
import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.view.NewSeminarViewImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class NewSeminarPresenter {
    private NewSeminarViewImpl view;

    public NewSeminarPresenter(NewSeminarViewImpl view) {
        this.view = view;

        List<String> categories = SeminarCategoryManager.getSeminarCategories().stream().map(SeminarCategory::getName).collect(Collectors.toList());
        view.setCategories(categories);
    }

    public void sendSeminarToBackend(NewSeminarDTO frontendObject) throws Exception {
        Seminar seminarToBeSaved = convertDTOtoModel(frontendObject);
        SeminarManager.createSeminar(seminarToBeSaved);
    }

    private Seminar convertDTOtoModel(NewSeminarDTO frontendObject) throws Exception {
        Seminar modelObject = new Seminar();

        modelObject.setTitle(frontendObject.getTitle());
        modelObject.setDescription(frontendObject.getDescription());
        modelObject.setLink(frontendObject.getLink());
        modelObject.setStreet(frontendObject.getStreet());
        modelObject.setHouseNumber(frontendObject.getHouseNumber());
        modelObject.setLocation(frontendObject.getLocation());

        //Here the conversion happens.
        modelObject.setPlz(frontendObject.getPlz().intValue());
        modelObject.setDate(LocalDateTime.of(frontendObject.getDate(), frontendObject.getTime()));

        modelObject.setCategory(SeminarCategoryManager.getSeminarByName(frontendObject.getCategory()));
        return modelObject;
    }

}

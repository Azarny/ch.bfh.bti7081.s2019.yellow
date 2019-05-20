package ch.bfh.bti7081.presenter;


import ch.bfh.bti7081.model.dto.SeminarDTO;
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

    public void sendSeminarToBackend(SeminarDTO frontendObject) throws Exception {
        Seminar seminarToBeSaved = convertDTOtoModel(frontendObject);
        SeminarManager.createSeminar(seminarToBeSaved);
    }

    private Seminar convertDTOtoModel(SeminarDTO seminarDTO) throws Exception {
        Seminar modelObject = new Seminar();

        modelObject.setTitle(seminarDTO.getTitle());
        modelObject.setDescription(seminarDTO.getDescription());
        modelObject.setUrl(seminarDTO.getUrl());
        modelObject.setStreet(seminarDTO.getStreet());
        modelObject.setHouseNumber(seminarDTO.getHouseNumber());
        modelObject.setLocation(seminarDTO.getLocation());

        //Here the conversion happens.
        modelObject.setPlz(seminarDTO.getPlz().intValue());
        modelObject.setDate(LocalDateTime.of(seminarDTO.getDate(), seminarDTO.getTime()));

        modelObject.setCategory(SeminarCategoryManager.getSeminarByName(seminarDTO.getCategory()));
        return modelObject;
    }

}

package ch.bfh.bti7081.presenter;


import ch.bfh.bti7081.model.dto.SeminarDTO;
import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.view.NewSeminarView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class NewSeminarPresenter {
    private NewSeminarView view;
    @Autowired
    private SeminarManager seminarManager;
    @Autowired
    private SeminarCategoryManager seminarCategoryManager;

    public NewSeminarPresenter(NewSeminarView view) {
        this.view = view;
    }

    @PostConstruct
    public void init(){
        List<String> categories = seminarCategoryManager.getSeminarCategories().stream()
                .map(SeminarCategory::getName)
                .collect(Collectors.toList());
        view.setCategories(categories);
    }

    public void sendSeminarToBackend(SeminarDTO frontendObject) throws Exception {
        Seminar seminarToBeSaved = convertDTOtoModel(frontendObject);
        seminarManager.createSeminar(seminarToBeSaved);
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

        modelObject.setCategory(seminarCategoryManager.getSeminarByName(seminarDTO.getCategory()));
        return modelObject;
    }

    public HashMap<String, Integer> getValidationConstants(){
        HashMap<String, Integer> validationConstants = new HashMap<>();
        validationConstants.put("streetNumber", SeminarManager.MINSTREETNUMBERLENGTH);
        validationConstants.put("streetName", SeminarManager.MINSTREETLENGTH);
        validationConstants.put("title", SeminarManager.MINTITLELENGTH);
        validationConstants.put("description", SeminarManager.MINDESCRIPTIONLENGTH);
        validationConstants.put("location", SeminarManager.MINLOCATIONLENGTH);
        validationConstants.put("maxYearsInFuture", SeminarManager.MAXYEARSINFUTURE);
        return validationConstants;
    }
}

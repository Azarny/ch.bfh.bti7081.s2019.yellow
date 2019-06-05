package ch.bfh.bti7081.presenter;


import ch.bfh.bti7081.model.ValidationConstants;
import ch.bfh.bti7081.presenter.dto.SeminarDTO;
import ch.bfh.bti7081.presenter.dto.UserDTO;
import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.view.NewSeminarView;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewSeminarPresenter {
    private NewSeminarView view;
    @Autowired
    private SeminarManager seminarManager;
    @Autowired
    private SeminarCategoryManager seminarCategoryManager;
    @Autowired
    private UserPresenter userPresenter;

    public List<String> getSeminarCategories() {
        List<String> categories = seminarCategoryManager.getSeminarCategories().stream()
                .map(SeminarCategory::getName)
                .collect(Collectors.toList());
        return categories;
    }

    public void sendSeminarToBackend(SeminarDTO frontendObject) throws Exception {
        String userName = (String) VaadinSession.getCurrent().getAttribute("userName");
        if (userName != null || !userName.isEmpty()) {
            UserDTO user = userPresenter.getUserByUsername(userName);
            if (user != null) {
                // check if user is expert or moderator
                if (user.getPermission() >= 2) {
                    Seminar seminarToBeSaved = convertDTOtoModel(frontendObject);
                    seminarManager.createSeminar(seminarToBeSaved);
                } else {
                    throw new IllegalAccessError("the user isn't privileged to create a seminar");
                }
            } else {
                throw new IllegalArgumentException("no user with this username was found");
            }
        } else {
            throw new IllegalArgumentException("no user is logged in");
        }
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

        modelObject.setCategory(seminarCategoryManager.getSeminarCategoryByName(seminarDTO.getCategory()));
        return modelObject;
    }

    public int getMinStreetNumberLength() {
        return ValidationConstants.MIN_STREETNUMBER_LENGTH.value;
    }

    public int getMinStreetLength() {
        return ValidationConstants.MIN_STREET_LENGTH.value;
    }

    public int getMinDescriptionLength() {
        return ValidationConstants.MIN_DESCRIPTION_LENGTH.value;
    }

    public int getMinTitleLength() {
        return ValidationConstants.MIN_TITLE_LENGTH.value;
    }

    public int getMinLocationLength() {
        return ValidationConstants.MIN_LOCATION_LENGTH.value;
    }

    public int getMaxYearsInFuture() {
        return ValidationConstants.MAX_YEARS_IN_FUTURE.value;
    }

    public void setView(NewSeminarView view) {
        this.view = view;
    }

}

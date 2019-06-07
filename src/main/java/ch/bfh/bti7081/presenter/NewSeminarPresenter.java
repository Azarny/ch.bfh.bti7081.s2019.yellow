package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.ValidationConstants;
import ch.bfh.bti7081.presenter.dto.SeminarDTO;
import ch.bfh.bti7081.presenter.dto.UserDTO;
import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.view.NewSeminarView;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.model.GeocodingResult;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
    @Value("${healthApp.googleApiKey:NOKEYFOUND}")
    private String googleApiKey;
    @Autowired
    private UserPresenter userPresenter;

    /**
     * Is accessed by view to get the categories for a seminar.
     * Author: walty1
     *
     * @return stringlist of seminaries.
     */
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
                    enrichWithCoordinates(frontendObject);
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

    /**
     * Converts a SeminarDTO to Seminar object. (Just value mapping.)
     * Author: walty1
     *
     * @param seminarDTO
     * @return Seminar
     * @throws NoSuchFieldException Throws an exception if the category could not be set.
     */
    private Seminar convertDTOtoModel(SeminarDTO seminarDTO) throws Exception {
        Seminar modelObject = new Seminar();

        modelObject.setTitle(seminarDTO.getTitle());
        modelObject.setDescription(seminarDTO.getDescription());
        modelObject.setUrl(seminarDTO.getUrl());
        modelObject.setStreet(seminarDTO.getStreet());
        modelObject.setHouseNumber(seminarDTO.getHouseNumber());
        modelObject.setLocation(seminarDTO.getLocation());
        modelObject.setLocation_lat(seminarDTO.getLocation_lat());
        modelObject.setLocation_lng(seminarDTO.getLocation_lng());

        //Here the conversion happens.
        modelObject.setPlz(seminarDTO.getPlz().intValue());
        modelObject.setDate(LocalDateTime.of(seminarDTO.getDate(), seminarDTO.getTime()));

        modelObject.setCategory(seminarCategoryManager.getSeminarCategoryByName(seminarDTO.getCategory()));
        return modelObject;
    }

    /**
     * Enriches a SeminarDTO with coordinates corresponding to the address.
     * Author: walty1
     *
     * @param seminar
     * @throws ApiException Standard
     * @throws NotFoundException If the adress is not found...
     * @throws InterruptedException Standard
     * @throws IOException Standard
     */
    private void enrichWithCoordinates(SeminarDTO seminar)
            throws ApiException, InterruptedException, IOException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(googleApiKey)
                .build();
        String address = seminar.getStreet() + " " + seminar.getHouseNumber() + ", " +
                seminar.getPlz() + " " + seminar.getLocation();
        //Throws errors in case of connection problems.
        GeocodingResult[] results = GeocodingApi.geocode(context,
                address).await();
        //Multiple results could be found.
        //Additional user query could be interesting if problems with recognition exist.
        if (results.length > 0) {
            seminar.setLocation_lat(results[0].geometry.location.lat);
            seminar.setLocation_lng(results[0].geometry.location.lng);
        } else {
            throw new NotFoundException("Die angegebene Adresse konnte nicht gefunden werden. " +
                    "Bitte prüfen Sie ihre Eingaben.");
        }
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

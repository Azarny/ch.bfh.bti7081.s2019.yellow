package ch.bfh.bti7081.presenter;


import ch.bfh.bti7081.model.ValidationConstants;
import ch.bfh.bti7081.model.dto.SeminarDTO;
import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.view.NewSeminarView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.model.GeocodingResult;
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

    public List<String> getSeminarCategories() {
        List<String> categories = seminarCategoryManager.getSeminarCategories().stream()
                .map(SeminarCategory::getName)
                .collect(Collectors.toList());
        return categories;
    }

    public void sendSeminarToBackend(SeminarDTO frontendObject)
            throws NoSuchFieldException, InterruptedException, ApiException, IOException {
        frontendObject = enrichWithCoordinates(frontendObject);
        Seminar seminarToBeSaved = convertDTOtoModel(frontendObject);
        seminarManager.createSeminar(seminarToBeSaved);
    }

    private Seminar convertDTOtoModel(SeminarDTO seminarDTO) throws NoSuchFieldException {
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

        modelObject.setCategory(seminarCategoryManager.getSeminarByName(seminarDTO.getCategory()));
        return modelObject;
    }

    public SeminarDTO enrichWithCoordinates(SeminarDTO seminar)
            throws ApiException, InterruptedException, IOException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(googleApiKey)
                .build();
        String address = seminar.getStreet()+" "+seminar.getHouseNumber()+", "+
                seminar.getPlz()+" "+seminar.getLocation();
        GeocodingResult[] results =  GeocodingApi.geocode(context,
                address).await();
        if(results.length > 0){
            seminar.setLocation_lat(results[0].geometry.location.lat);
            seminar.setLocation_lng(results[0].geometry.location.lng);
            return seminar;
        }else{
            throw new NotFoundException("No coordinates were available for this place.");
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

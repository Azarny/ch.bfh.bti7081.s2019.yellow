package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.presenter.dto.SeminarDTO;
import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
import ch.bfh.bti7081.presenter.dto.SeminarFilterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SeminarPresenter {
    @Autowired
    private SeminarCategoryManager seminarCategoryManager;
    @Autowired
    private SeminarManager seminarManager;

    public List<String> getSeminarCategories() {
        List<String> categories = seminarCategoryManager.getSeminarCategories().stream()
                .map(SeminarCategory::getName)
                .collect(Collectors.toList());
        return categories;
    }

    private List<Seminar> getSeminaries() {
        return seminarManager.getSeminaries();
    }

    private List<Seminar> getFilteredSeminaries(SeminarFilter filter) {
        return seminarManager.getFilteredSeminars(filter);
    }
    public List<SeminarDTO> getSeminarDtos() throws Exception{
        return convertSeminarModelsToDtos(getSeminaries());
    }
    public List<SeminarDTO> getFilteredSeminarDtos(SeminarFilterDTO filter) throws Exception{

        return convertSeminarModelsToDtos(getFilteredSeminaries(convertFilterDtoToModel(filter)));
    }

    private List<SeminarDTO> convertSeminarModelsToDtos(List<Seminar> seminaries) throws Exception{
        List<SeminarDTO> seminarDtos = new ArrayList<>();
        for (Seminar modelObject : seminaries) {
            SeminarDTO seminarDTO = new SeminarDTO();
            seminarDTO.setCategory(modelObject.getCategory().getName());
            seminarDTO.setDate(modelObject.getDate().toLocalDate());
            seminarDTO.setDescription(modelObject.getDescription());
            seminarDTO.setHouseNumber(modelObject.getHouseNumber());
            seminarDTO.setLocation(modelObject.getLocation());
            seminarDTO.setPlz(modelObject.getPlz().doubleValue());
            seminarDTO.setStreet(modelObject.getStreet());
            seminarDTO.setTime(modelObject.getDate().toLocalTime());
            seminarDTO.setTitle(modelObject.getTitle());
            seminarDTO.setUrl(modelObject.getUrl());
            seminarDTO.setLocation_lat(modelObject.getLocation_lat());
            seminarDTO.setLocation_lng(modelObject.getLocation_lng());
            seminarDtos.add(seminarDTO);
        }
        return seminarDtos;
    }

    /**
     * Converts FilterDTO to Model
     *
     * @param seminarFilterDTO SeminarfilterDTO
     * @return SeminarFilter
     * @author oppls7
     */
    private SeminarFilter convertFilterDtoToModel(SeminarFilterDTO seminarFilterDTO){
        SeminarFilter model = new SeminarFilter();
        model.setKeyword(seminarFilterDTO.getKeyword());
        model.setFromDate(seminarFilterDTO.getFromDate());
        model.setToDate(seminarFilterDTO.getToDate());
        model.setLocation(seminarFilterDTO.getLocation());
        if(seminarFilterDTO.getCategory()!=null) {
            model.setCategory(seminarCategoryManager.getSeminarCategoryByName(seminarFilterDTO.getCategory()));
        }
        return model;
    }

    /**
     * Resets the Filter-Values
     *
     * @param seminarFilterDTO SeminarFilterDTO
     * @return SeminarFilter
     * @author oppls7
     */
    public SeminarFilterDTO reset(SeminarFilterDTO seminarFilterDTO){
        seminarFilterDTO.setCategory(null);
        seminarFilterDTO.setKeyword("");
        seminarFilterDTO.setFromDate(null);
        seminarFilterDTO.setLocation("");
        seminarFilterDTO.setToDate(null);
        return seminarFilterDTO;
    }
}

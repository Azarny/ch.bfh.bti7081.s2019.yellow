package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
import ch.bfh.bti7081.presenter.dto.SeminarFilterDTO;
import ch.bfh.bti7081.presenter.dto.SeminarDTO;
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

    /**
     * Filters the seminaries.
     *
     * @param filter filter-object
     * @return seminar-list based on filter (no DTO)
     * @author luscm1
     */
    private List<Seminar> getFilteredSeminaries(SeminarFilter filter) {
        return seminarManager.getFilteredSeminars(filter);
    }

    /**
     * Delivers all seminaries in DTO form.
     *
     * @return list of all seminaries
     * @author oppls7
     */
    public List<SeminarDTO> getSeminarDtos(){
        return convertSeminarModelsToDtos(getSeminaries());
    }

    /**
     * Delivers a list of seminaries based on a filter.
     *
     * @param filter filter object filled out by the user.
     * @return list of seminaries (DTO)
     * @author oppls7
     */
    public List<SeminarDTO> getFilteredSeminarDtos(SeminarFilterDTO filter){

        return convertSeminarModelsToDtos(getFilteredSeminaries(convertFilterDtoToModel(filter)));
    }

    /**
     * Converts a seminar (in a a list) to a seminar DTO
     *
     * @param seminaries list of seminaries
     * @return list of SeminarDTO's
     * @author oppls7
     */
    private List<SeminarDTO> convertSeminarModelsToDtos(List<Seminar> seminaries) throws Exception{
        List<SeminarDTO> seminarDtos = new ArrayList<>();
        for (Seminar modelObject : seminaries) {
            SeminarDTO seminarDTO = new SeminarDTO();
            seminarDTO.setId(modelObject.getId());
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

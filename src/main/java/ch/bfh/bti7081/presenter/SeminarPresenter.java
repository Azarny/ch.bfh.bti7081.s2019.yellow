package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
import ch.bfh.bti7081.presenter.dto.SeminarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeminarPresenter {
    @Autowired
    private SeminarCategoryManager seminarCategoryManager;
    @Autowired
    private SeminarManager seminarManager;

    public List<SeminarCategory> getSeminarCategories() {
        return seminarCategoryManager.getSeminarCategories();
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
    public List<SeminarDTO> getSeminarDtos() {
        return convertModelsToDtos(getSeminaries());
    }

    /**
     * Delivers a list of seminaries based on a filter.
     *
     * @param filter filter object filled out by the user.
     * @return list of seminaries (DTO)
     * @author oppls7
     */
    public List<SeminarDTO> getFilteredSeminarDtos(SeminarFilter filter) {

        return convertModelsToDtos(getFilteredSeminaries(filter));
    }

    /**
     * Converts a seminar (in a a list) to a seminar DTO
     *
     * @param seminaries list of seminaries
     * @return list of SeminarDTO's
     * @author oppls7
     */
    private List<SeminarDTO> convertModelsToDtos(List<Seminar> seminaries) {
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
}

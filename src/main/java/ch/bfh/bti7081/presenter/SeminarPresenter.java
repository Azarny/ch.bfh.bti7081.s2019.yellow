package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.dto.SeminarDTO;
import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
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

    private List<Seminar> getFilteredSeminaries(SeminarFilter filter) {
        return seminarManager.getFilteredSeminars(filter);
    }
    public List<SeminarDTO> getSeminarDtos() throws Exception{
        return convertModelsToDtos(getSeminaries());
    }
    public List<SeminarDTO> getFilteredSeminarDtos(SeminarFilter filter) throws Exception{

        return convertModelsToDtos(getFilteredSeminaries(filter));
    }

    private List<SeminarDTO> convertModelsToDtos(List<Seminar> seminaries) throws Exception{
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
}

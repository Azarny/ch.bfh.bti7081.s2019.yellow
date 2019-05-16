import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FilterTest {
    private List<Seminar> seminaries = SeminarManager.getSeminaries();
    private SeminarFilter seminarFilter= new SeminarFilter();

    @Test
    public void CategoryFilter(){
        int seminarCount = seminaries.size();
        SeminarCategory categoryToFilter = seminaries.get(1).getCategory();
        int seminariesWithFilteredCategory = 0;
        seminarFilter.setCategory(categoryToFilter);
        for (Seminar seminar : seminaries) {
            if (seminar.getCategory().getName().equals(categoryToFilter.getName())) seminariesWithFilteredCategory ++;
        }
        List<Seminar> filteredSeminaries = SeminarManager.getFilteredSeminars(seminarFilter);
        Assert.assertEquals( seminarCount-seminariesWithFilteredCategory, filteredSeminaries.size());
    }
}
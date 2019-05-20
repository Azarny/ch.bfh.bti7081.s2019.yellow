import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class SeminarManagerTest {
    private SeminarManager seminarManager = new SeminarManager();
    private SeminarCategoryManager seminarCategoryManager = new SeminarCategoryManager();

    private List<Seminar> seminaries = seminarManager.getSeminaries();
    private SeminarFilter seminarFilter = new SeminarFilter();
    private int seminarCount = seminaries.size();
    private int seminariesWithFilteredCategory = 0;

    @Test
    public void categoryFilter() {
        int seminarCount = seminaries.size();
        SeminarCategory categoryToFilter = seminaries.get(1).getCategory();
        int seminariesWithFilteredCategory = 0;
        seminarFilter.setCategory(categoryToFilter);
        for (Seminar seminar : seminaries) {
            if (seminar.getCategory().getName().equals(categoryToFilter.getName())) seminariesWithFilteredCategory++;
        }
        List<Seminar> filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        Assert.assertEquals(seminarCount - seminariesWithFilteredCategory, filteredSeminaries.size());
    }

    @Test
    public void locationFilter() {
        seminarFilter.setLocation(seminaries.get(1).getLocation());
        for (Seminar seminar : seminaries) {
            if (seminar.getLocation().contains(seminarFilter.getLocation())) seminariesWithFilteredCategory++;
        }
        List<Seminar> filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        Assert.assertEquals(seminarCount - seminariesWithFilteredCategory, filteredSeminaries.size());
    }

    @Test
    public void locationFilterCaseInsensitive() {
        seminarFilter.setLocation(seminaries.get(1).getLocation());
        for (Seminar seminar : seminaries) {
            if (seminar.getLocation().toLowerCase().contains(seminarFilter.getLocation().toLowerCase()))
                seminariesWithFilteredCategory++;
        }
        List<Seminar> filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        Assert.assertEquals(seminarCount - seminariesWithFilteredCategory, filteredSeminaries.size());
    }

    @Test
    public void dateFilterTo() {

        // test with min date
        seminarFilter.setToDate(seminaries.stream().map(Seminar::getDate).min(LocalDateTime::compareTo).get().toLocalDate());
        for (Seminar seminar : seminaries) {
            if (seminar.getDate().isAfter(seminarFilter.getToDate().atStartOfDay().plusDays(1).minusSeconds(1)))
                seminariesWithFilteredCategory++;
        }
        List<Seminar> filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        Assert.assertEquals(seminarCount - seminariesWithFilteredCategory, filteredSeminaries.size());

        seminariesWithFilteredCategory = 0;
        //test with max date
        seminarFilter.setToDate(seminaries.stream().map(Seminar::getDate).max(LocalDateTime::compareTo).get().toLocalDate());
        for (Seminar seminar : seminaries) {
            if (seminar.getDate().isAfter(seminarFilter.getToDate().atStartOfDay().plusDays(1).minusSeconds(1)))
                seminariesWithFilteredCategory++;
        }
        filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        Assert.assertEquals(seminarCount - seminariesWithFilteredCategory, filteredSeminaries.size());
    }

    @Test
    public void dateFilterFrom() {
        //test with min date
        seminarFilter.setFromDate(seminaries.stream().map(Seminar::getDate).min(LocalDateTime::compareTo).get().toLocalDate());
        for (Seminar seminar : seminaries) {
            if (seminar.getDate().isBefore(seminarFilter.getFromDate().atStartOfDay()))
                seminariesWithFilteredCategory++;
        }
        List<Seminar> filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        Assert.assertEquals(seminarCount - seminariesWithFilteredCategory, filteredSeminaries.size());

        seminariesWithFilteredCategory = 0;
        //test with max date
        seminarFilter.setFromDate(seminaries.stream().map(Seminar::getDate).max(LocalDateTime::compareTo).get().toLocalDate());
        for (Seminar seminar : seminaries) {
            if (seminar.getDate().isBefore(seminarFilter.getFromDate().atStartOfDay()))
                seminariesWithFilteredCategory++;
        }
        filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        Assert.assertEquals(seminarCount - seminariesWithFilteredCategory, filteredSeminaries.size());
    }

    @Test
    public void keywordFilter() {
        String keyword = seminaries.get(3).getTitle().split(" ")[0];
        //one keyword
        seminarFilter.setKeyword(keyword);
        keyword = keyword.toLowerCase();
        List<Seminar> filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        for (Seminar seminar : seminaries) {
            if (seminar.getTitle().toLowerCase().contains(keyword) || seminar.getLocation().toLowerCase().contains(keyword) || seminar.getDescription().toLowerCase().contains(keyword)) {
                seminariesWithFilteredCategory++;
            }
        }
        Assert.assertEquals(seminariesWithFilteredCategory, filteredSeminaries.size());

        seminariesWithFilteredCategory = 0;
        //two keywords
        keyword = keyword + " Phobie";
        seminarFilter.setKeyword(keyword);
        keyword = keyword.toLowerCase();
        filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        String[] keywords = keyword.split(" ");
        for (Seminar seminar : seminaries) {
            if (seminar.getTitle().toLowerCase().contains(keywords[0]) ||
                    seminar.getLocation().toLowerCase().contains(keywords[0]) ||
                    seminar.getDescription().toLowerCase().contains(keywords[0]) ||
                    seminar.getTitle().toLowerCase().contains(keywords[1]) ||
                    seminar.getLocation().toLowerCase().contains(keywords[1]) ||
                    seminar.getDescription().toLowerCase().contains(keywords[1])) {
                seminariesWithFilteredCategory++;
            }
        }
        Assert.assertEquals(seminariesWithFilteredCategory, filteredSeminaries.size());
    }

}
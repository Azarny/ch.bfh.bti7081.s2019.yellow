import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
import org.junit.Assert;
import org.junit.Before;
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


    private Seminar testSeminar= new Seminar();
    private SeminarManager manager = new SeminarManager();
    
    @Before
    public void setup(){
        testSeminar.setStreet("Strasse mit Ümläütén");
        testSeminar.setHouseNumber("1");
        testSeminar.setPlz(3132);
        testSeminar.setLocation("Location mit Ümläütén");
        testSeminar.setTitle("Title mit Ümläütén");
        testSeminar.setDate(LocalDateTime.now().plusDays(1));
        testSeminar.setCategory(new SeminarCategory("test"));
        testSeminar.setUrl("http://test.org");
        testSeminar.setDescription("Description mit Ümläütén");
    }


    // tests for street
    @Test
    public void testStreetOK(){
        testSeminar.setStreet("Strasse mit Ümläütén");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setStreet("Str4sse mit Z4hl3n");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setStreet("Dies ist ein sehr sehr langer Strassenname denn es muss getestet werden ob auch sehr sehr lange Strassennamen funktionieren");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setStreet("Abkürzungsstr.");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setStreet("Ey");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));

    }
    @Test
    public void testStreetNOK(){
        testSeminar.setStreet("");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("street"));
        testSeminar.setStreet(null);
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("street"));
        testSeminar.setStreet("      ");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("street"));
        testSeminar.setStreet("A");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("street"));
    }

    //tests for houseNumber
    @Test
    public void testHouseNumberOK(){
        testSeminar.setHouseNumber("1");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setHouseNumber("12");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setHouseNumber("1232353241234353");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setHouseNumber("1A");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setHouseNumber("1a");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
    }
    @Test
    public void testHouseNumberNOK(){
        testSeminar.setHouseNumber("");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("house number"));
        testSeminar.setHouseNumber("123s123");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("house number"));
        testSeminar.setHouseNumber(null);
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("house number"));
        testSeminar.setHouseNumber("   ");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("house number"));
    }

    //test for PLZ
    @Test
    public void testPlzOK(){
        testSeminar.setPlz(3132);
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setPlz(9999);
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setPlz(1000);
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setPlz(100000);
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setPlz(123456);
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setPlz(999999);
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
    }
    @Test
    public void testPlzNOK(){
        testSeminar.setPlz(0);
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("PLZ"));
        testSeminar.setPlz(null);
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("PLZ"));
        testSeminar.setPlz(12);
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("PLZ"));
        testSeminar.setPlz(1221314115);testSeminar.setPlz(0);
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("PLZ"));
    }


    //test for location
    @Test
    public void testLocationOK(){
        testSeminar.setLocation("Location mit Ümläütén");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setLocation("Locati0n mit Z4hl3n");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setLocation("Dies ist ein sehr sehr langer Locationnname denn es muss getestet werden ob auch sehr sehr lange Locationnnamen funktionieren");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setLocation("Abkürzungsloc.");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setLocation("Ey");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));

    }
    @Test
    public void testLocationNOK(){
        testSeminar.setLocation("");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("location"));
        testSeminar.setLocation(null);
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("location"));
        testSeminar.setLocation("      ");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("location"));
        testSeminar.setLocation("A");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("location"));
    }


    //test for title
    @Test
    public void testTitleOK(){
        testSeminar.setTitle("Title mit Ümläütén");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setTitle("Title mit Z4hl3n");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setTitle("Dies ist ein sehr sehr langer Titlenname denn es muss getestet werden ob auch sehr sehr lange Titlennamen funktionieren");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setTitle("Abkürzungstitle.");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));

    }
    @Test
    public void testTitleNOK(){
        testSeminar.setTitle("");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("title"));
        testSeminar.setTitle(null);
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("title"));
        testSeminar.setTitle("      ");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("title"));
        testSeminar.setTitle("A");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("title"));
    }


    //test for date
    @Test
    public void testDateOK(){
        testSeminar.setDate(LocalDateTime.now().plusDays(1));
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setDate(LocalDateTime.now().plusMonths(1));
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setDate(LocalDateTime.now().plusHours(1));
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setDate(LocalDateTime.now().plusMinutes(1));
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setDate(LocalDateTime.now().plusYears(4));
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
    }

    @Test
    public void testDateNOK(){
        testSeminar.setDate(LocalDateTime.now().minusMinutes(1));
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("date"));
        testSeminar.setDate(LocalDateTime.now().plusYears(6));
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("date"));
    }


    //test for category
    @Test
    public void testCategoryOK(){
        testSeminar.setCategory(new SeminarCategory("test"));
        Assert.assertEquals("", manager.validateSeminar(testSeminar));

    }

    @Test
    public void testCategoryNOK(){
        testSeminar.setCategory(null);
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("category"));
    }

    //test for url
    @Test
    public void testUrlOK(){
        testSeminar.setUrl("https://docs.jboss.org/hibernate/beanvalidation/spec/2.0/api/javax/validation/constraints/NotBlank.html");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setUrl("https://www.vogella.com/tutorials/JUnit/article.html");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setUrl("www.google.ch");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setUrl("http://test.org");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
    }
    @Test
    public void testUrlNOK(){
        testSeminar.setUrl("");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("URL"));
        testSeminar.setUrl(null);
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("URL"));
        testSeminar.setUrl("   ");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("URL"));
        testSeminar.setUrl("A");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("URL"));
        testSeminar.setUrl("www.göögle.com");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("URL"));
    }


    //test for description
    @Test
    public void testDescriptionOK(){
        testSeminar.setDescription("Description mit Ümläütén");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setDescription("Description mit Z4hl3n");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setDescription("Dies ist ein sehr sehr langer Descriptionnname denn es muss getestet werden ob auch sehr sehr lange Descriptionnnamen funktionieren");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));
        testSeminar.setDescription("AbkürzungsDescription.");
        Assert.assertEquals("", manager.validateSeminar(testSeminar));

    }
    @Test
    public void testDescriptionNOK(){
        testSeminar.setDescription("");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("description"));
        testSeminar.setDescription(null);
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("description"));
        testSeminar.setDescription("      ");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("description"));
        testSeminar.setDescription("A");
        Assert.assertTrue(manager.validateSeminar(testSeminar).contains("description"));
    }
}
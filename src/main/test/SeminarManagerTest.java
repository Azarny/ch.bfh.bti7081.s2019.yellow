import ch.bfh.bti7081.App;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class SeminarManagerTest {
    @Autowired
    private SeminarManager seminarManager;

    Seminar testSeminar = new Seminar();
    ;

    @Test
    public void categoryFilter() {
        SeminarFilter seminarFilter = new SeminarFilter();
        List<Seminar> seminaries = seminarManager.getSeminaries();
        SeminarCategory categoryToFilter = seminaries.get(0).getCategory();
        int seminariesWithFilteredCategory = 0;
        seminarFilter.setCategory(categoryToFilter);
        for (Seminar seminar : seminaries) {
            if (seminar.getCategory().getName().equals(categoryToFilter.getName())) seminariesWithFilteredCategory++;
        }
        List<Seminar> filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        Assert.assertEquals(seminaries.size() - seminariesWithFilteredCategory, filteredSeminaries.size());
    }

    @Test
    public void locationFilter() {
        List<Seminar> seminaries = seminarManager.getSeminaries();
        SeminarFilter seminarFilter = new SeminarFilter();
        int seminariesWithFilteredCategory = 0;
        seminarFilter.setLocation(seminaries.get(0).getLocation());
        for (Seminar seminar : seminaries) {
            if (seminar.getLocation().contains(seminarFilter.getLocation())) seminariesWithFilteredCategory++;
        }
        List<Seminar> filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        Assert.assertEquals(seminaries.size() - seminariesWithFilteredCategory, filteredSeminaries.size());
    }

    @Test
    public void locationFilterCaseInsensitive() {
        List<Seminar> seminaries = seminarManager.getSeminaries();
        int seminariesWithFilteredCategory = 0;
        SeminarFilter seminarFilter = new SeminarFilter();
        seminarFilter.setLocation(seminaries.get(0).getLocation());
        for (Seminar seminar : seminaries) {
            if (seminar.getLocation().toLowerCase().contains(seminarFilter.getLocation().toLowerCase()))
                seminariesWithFilteredCategory++;
        }
        List<Seminar> filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        Assert.assertEquals(seminaries.size() - seminariesWithFilteredCategory, filteredSeminaries.size());
    }

    @Test
    public void dateFilterTo() {
        List<Seminar> seminaries = seminarManager.getSeminaries();
        int seminariesWithFilteredCategory = 0;
        SeminarFilter seminarFilter = new SeminarFilter();
        // test with min date
        seminarFilter.setToDate(seminaries.stream().map(Seminar::getDate).min(LocalDateTime::compareTo).get().toLocalDate());
        List<Seminar> filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        Assert.assertEquals(0, filteredSeminaries.size());

        seminariesWithFilteredCategory = 0;
        //test with max date
        seminarFilter.setToDate(seminaries.stream().map(Seminar::getDate).max(LocalDateTime::compareTo).get().toLocalDate());
        filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        Assert.assertEquals(seminaries.size() - 1, filteredSeminaries.size());
    }

    @Test
    public void dateFilterFrom() {
        List<Seminar> seminaries = seminarManager.getSeminaries();
        int seminariesWithFilteredCategory = 0;
        SeminarFilter seminarFilter = new SeminarFilter();
        //test with min date
        seminarFilter.setFromDate(seminaries.stream().map(Seminar::getDate).min(LocalDateTime::compareTo).get().toLocalDate());
        for (Seminar seminar : seminaries) {
            if (seminar.getDate().isBefore(seminarFilter.getFromDate().atStartOfDay()))
                seminariesWithFilteredCategory++;
        }
        List<Seminar> filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        Assert.assertEquals(seminaries.size() - seminariesWithFilteredCategory, filteredSeminaries.size());

        seminariesWithFilteredCategory = 0;
        //test with max date
        seminarFilter.setFromDate(seminaries.stream().map(Seminar::getDate).max(LocalDateTime::compareTo).get().toLocalDate());
        for (Seminar seminar : seminaries) {
            if (seminar.getDate().isBefore(seminarFilter.getFromDate().atStartOfDay()))
                seminariesWithFilteredCategory++;
        }
        filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        Assert.assertEquals(seminaries.size() - seminariesWithFilteredCategory, filteredSeminaries.size());
    }

    @Test
    public void keywordFilter() {
        List<Seminar> seminaries = seminarManager.getSeminaries();
        int seminariesWithFilteredCategory = 0;
        String keyword = "Wie";
        SeminarFilter seminarFilter = new SeminarFilter();
        //one keyword
        seminarFilter.setKeyword(keyword);
        keyword = keyword.toLowerCase();
        List<Seminar> filteredSeminaries = seminarManager.getFilteredSeminars(seminarFilter);
        for (Seminar seminar : seminaries) {
            if (seminar.getTitle().toLowerCase().contains(keyword) || seminar.getLocation().toLowerCase().contains(keyword) || seminar.getDescription().toLowerCase().contains(keyword)) {
                seminariesWithFilteredCategory++;
            }
        }
        Assert.assertEquals(seminaries.size() - seminariesWithFilteredCategory, filteredSeminaries.size());

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
        Assert.assertEquals(seminaries.size() - seminariesWithFilteredCategory, filteredSeminaries.size());
    }

    @Before
    public void setup() {
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
    public void testStreetOK() {
        testSeminar.setStreet("Strasse mit Ümläütén");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setStreet("Str4sse mit Z4hl3n");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setStreet("Dies ist ein sehr sehr langer Strassenname denn es muss getestet werden ob auch sehr sehr lange Strassennamen funktionieren");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setStreet("Abkürzungsstr.");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setStreet("Ey");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));

    }

    @Test
    public void testStreetNOK() {
        testSeminar.setStreet("");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Strasse"));
        testSeminar.setStreet(null);
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Strasse"));
        testSeminar.setStreet("      ");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Strasse"));
        testSeminar.setStreet("A");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Strasse"));
    }

    //tests for houseNumber
    @Test
    public void testHouseNumberOK() {
        testSeminar.setHouseNumber("1");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setHouseNumber("12");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setHouseNumber("1232353241234353");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setHouseNumber("1A");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setHouseNumber("1a");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
    }

    @Test
    public void testHouseNumberNOK() {
        testSeminar.setHouseNumber("");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Strassennummer"));
        testSeminar.setHouseNumber("123s123");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Strassennummer"));
        testSeminar.setHouseNumber(null);
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Strassennummer"));
        testSeminar.setHouseNumber("   ");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Strassennummer"));
    }

    //test for PLZ
    @Test
    public void testPlzOK() {
        testSeminar.setPlz(3132);
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setPlz(9999);
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setPlz(1000);
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setPlz(100000);
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setPlz(123456);
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setPlz(999999);
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
    }

    @Test
    public void testPlzNOK() {
        testSeminar.setPlz(0);
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("PLZ"));
        testSeminar.setPlz(null);
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("PLZ"));
        testSeminar.setPlz(12);
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("PLZ"));
        testSeminar.setPlz(1221314115);
        testSeminar.setPlz(0);
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("PLZ"));
    }


    //test for location
    @Test
    public void testLocationOK() {
        testSeminar.setLocation("Location mit Ümläütén");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setLocation("Locati0n mit Z4hl3n");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setLocation("Dies ist ein sehr sehr langer Locationnname denn es muss getestet werden ob auch sehr sehr lange Locationnnamen funktionieren");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setLocation("Abkürzungsloc.");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setLocation("Ey");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));

    }

    @Test
    public void testLocationNOK() {
        testSeminar.setLocation("");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Ort"));
        testSeminar.setLocation(null);
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Ort"));
        testSeminar.setLocation("      ");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Ort"));
        testSeminar.setLocation("A");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Ort"));
    }


    //test for title
    @Test
    public void testTitleOK() {
        testSeminar.setTitle("Title mit Ümläütén");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setTitle("Title mit Z4hl3n");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setTitle("Dies ist ein sehr sehr langer Titlenname denn es muss getestet werden ob auch sehr sehr lange Titlennamen funktionieren");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setTitle("Abkürzungstitle.");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));

    }

    @Test
    public void testTitleNOK() {
        testSeminar.setTitle("");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Titel"));
        testSeminar.setTitle(null);
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Titel"));
        testSeminar.setTitle("      ");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Titel"));
        testSeminar.setTitle("A");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Titel"));
    }


    //test for date
    @Test
    public void testDateOK() {
        testSeminar.setDate(LocalDateTime.now().plusDays(1));
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setDate(LocalDateTime.now().plusMonths(1));
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setDate(LocalDateTime.now().plusHours(1));
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setDate(LocalDateTime.now().plusMinutes(1));
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setDate(LocalDateTime.now().plusYears(4));
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
    }

    @Test
    public void testDateNOK() {
        testSeminar.setDate(LocalDateTime.now().minusMinutes(1));
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Datum"));
        testSeminar.setDate(LocalDateTime.now().plusYears(6));
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Datum"));
    }


    //test for category
    @Test
    public void testCategoryOK() {
        testSeminar.setCategory(new SeminarCategory("test"));
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));

    }

    @Test
    public void testCategoryNOK() {
        testSeminar.setCategory(null);
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Kategorie"));
    }

    //test for url
    @Test
    public void testUrlOK() {
        testSeminar.setUrl("https://docs.jboss.org/hibernate/beanvalidation/spec/2.0/api/javax/validation/constraints/NotBlank.html");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setUrl("https://www.vogella.com/tutorials/JUnit/article.html");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setUrl("www.google.ch");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setUrl("http://test.org");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
    }

    @Test
    public void testUrlNOK() {
        testSeminar.setUrl("");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Link"));
        testSeminar.setUrl(null);
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Link"));
        testSeminar.setUrl("   ");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Link"));
        testSeminar.setUrl("A");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Link"));
        testSeminar.setUrl("www.göögle.com");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Link"));
    }


    //test for description
    @Test
    public void testDescriptionOK() {
        testSeminar.setDescription("Description mit Ümläütén");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setDescription("Description mit Z4hl3n");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setDescription("Dies ist ein sehr sehr langer Descriptionnname denn es muss getestet werden ob auch sehr sehr lange Descriptionnnamen funktionieren");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));
        testSeminar.setDescription("AbkürzungsDescription.");
        Assert.assertEquals("", seminarManager.validateSeminar(testSeminar));

    }

    @Test
    public void testDescriptionNOK() {
        testSeminar.setDescription("");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Beschreibung"));
        testSeminar.setDescription(null);
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Beschreibung"));
        testSeminar.setDescription("      ");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Beschreibung"));
        testSeminar.setDescription("A");
        Assert.assertTrue(seminarManager.validateSeminar(testSeminar).contains("Beschreibung"));
    }
}
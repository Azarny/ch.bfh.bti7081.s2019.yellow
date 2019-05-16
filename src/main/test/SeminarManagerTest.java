import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import static org.junit.Assert.fail;

public class SeminarManagerTest {
    private Seminar testSeminar= new Seminar();
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
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setStreet("Str4sse mit Z4hl3n");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setStreet("Dies ist ein sehr sehr langer Strassenname denn es muss getestet werden ob auch sehr sehr lange Strassennamen funktionieren");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setStreet("Abkürzungsstr.");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setStreet("Ey");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));

    }
    @Test
    public void testStreetNOK(){
        testSeminar.setStreet("");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("street"));
        testSeminar.setStreet(null);
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("street"));
        testSeminar.setStreet("      ");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("street"));
        testSeminar.setStreet("A");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("street"));
    }

    //tests for houseNumber
    @Test
    public void testHouseNumberOK(){
        testSeminar.setHouseNumber("1");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setHouseNumber("12");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setHouseNumber("1232353241234353");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setHouseNumber("1A");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setHouseNumber("1a");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
    }
    @Test
    public void testHouseNumberNOK(){
        testSeminar.setHouseNumber("");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("house number"));
        testSeminar.setHouseNumber("123s123");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("house number"));
        testSeminar.setHouseNumber(null);
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("house number"));
        testSeminar.setHouseNumber("   ");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("house number"));
    }

    //test for PLZ
    @Test
    public void testPlzOK(){
        testSeminar.setPlz(3132);
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setPlz(9999);
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setPlz(1000);
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setPlz(100000);
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setPlz(123456);
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setPlz(999999);
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
    }
    @Test
    public void testPlzNOK(){
        testSeminar.setPlz(0);
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("PLZ"));
        testSeminar.setPlz(null);
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("PLZ"));
        testSeminar.setPlz(12);
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("PLZ"));
        testSeminar.setPlz(1221314115);testSeminar.setPlz(0);
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("PLZ"));
    }


    //test for location
    @Test
    public void testLocationOK(){
        testSeminar.setLocation("Location mit Ümläütén");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setLocation("Locati0n mit Z4hl3n");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setLocation("Dies ist ein sehr sehr langer Locationnname denn es muss getestet werden ob auch sehr sehr lange Locationnnamen funktionieren");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setLocation("Abkürzungsloc.");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setLocation("Ey");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));

    }
    @Test
    public void testLocationNOK(){
        testSeminar.setLocation("");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("location"));
        testSeminar.setLocation(null);
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("location"));
        testSeminar.setLocation("      ");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("location"));
        testSeminar.setLocation("A");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("location"));
    }


    //test for title
    @Test
    public void testTitleOK(){
        testSeminar.setTitle("Title mit Ümläütén");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setTitle("Title mit Z4hl3n");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setTitle("Dies ist ein sehr sehr langer Titlenname denn es muss getestet werden ob auch sehr sehr lange Titlennamen funktionieren");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setTitle("Abkürzungstitle.");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));

    }
    @Test
    public void testTitleNOK(){
        testSeminar.setTitle("");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("title"));
        testSeminar.setTitle(null);
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("title"));
        testSeminar.setTitle("      ");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("title"));
        testSeminar.setTitle("A");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("title"));
    }


    //test for date
    @Test
    public void testDateOK(){
        testSeminar.setDate(LocalDateTime.now().plusDays(1));
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setDate(LocalDateTime.now().plusMonths(1));
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setDate(LocalDateTime.now().plusHours(1));
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setDate(LocalDateTime.now().plusMinutes(1));
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setDate(LocalDateTime.now().plusYears(4));
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
    }

    @Test
    public void testDateNOK(){
        testSeminar.setDate(LocalDateTime.now().minusMinutes(1));
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("date"));
        testSeminar.setDate(LocalDateTime.now().plusYears(6));
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("date"));
    }


    //test for category
    @Test
    public void testCategoryOK(){
        testSeminar.setCategory(new SeminarCategory("test"));
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));

    }

    @Test
    public void testCategoryNOK(){
        testSeminar.setCategory(null);
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("category"));
    }

    //test for url
    @Test
    public void testUrlOK(){
        testSeminar.setUrl("https://docs.jboss.org/hibernate/beanvalidation/spec/2.0/api/javax/validation/constraints/NotBlank.html");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setUrl("https://www.vogella.com/tutorials/JUnit/article.html");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setUrl("www.google.ch");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setUrl("http://test.org");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
    }
    @Test
    public void testUrlNOK(){
        testSeminar.setUrl("");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("URL"));
        testSeminar.setUrl(null);
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("URL"));
        testSeminar.setUrl("   ");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("URL"));
        testSeminar.setUrl("A");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("URL"));
        testSeminar.setUrl("www.göögle.com");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("URL"));
    }


    //test for description
    @Test
    public void testDescriptionOK(){
        testSeminar.setDescription("Description mit Ümläütén");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setDescription("Description mit Z4hl3n");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setDescription("Dies ist ein sehr sehr langer Descriptionnname denn es muss getestet werden ob auch sehr sehr lange Descriptionnnamen funktionieren");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));
        testSeminar.setDescription("AbkürzungsDescription.");
        Assert.assertEquals("", SeminarManager.validateSeminar(testSeminar));

    }
    @Test
    public void testDescriptionNOK(){
        testSeminar.setDescription("");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("description"));
        testSeminar.setDescription(null);
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("description"));
        testSeminar.setDescription("      ");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("description"));
        testSeminar.setDescription("A");
        Assert.assertTrue(SeminarManager.validateSeminar(testSeminar).contains("description"));
    }
}
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import static org.junit.Assert.fail;

public class SeminarValidationTest{
    private Seminar testSeminar= new Seminar();

    // tests for street
    @Test
    public void testStreetOK(){
        Assert.assertTrue(testSeminar.setStreet("Strasse mit Ümläütén"));
        Assert.assertTrue(testSeminar.setStreet("Str4sse mit Z4hl3n"));
        Assert.assertTrue(testSeminar.setStreet("Dies ist ein sehr sehr langer Strassenname denn es muss getestet werden ob auch sehr sehr lange Strassennamen funktionieren"));
        Assert.assertTrue(testSeminar.setStreet("Abkürzungsstr."));
        Assert.assertTrue(testSeminar.setStreet("Ey"));

    }
    @Test
    public void testStreetNOK(){
        Assert.assertFalse(testSeminar.setStreet(""));
        Assert.assertFalse(testSeminar.setStreet(null));
        Assert.assertFalse(testSeminar.setStreet("      "));
        Assert.assertFalse(testSeminar.setStreet("A"));
    }

    //tests for houseNumber
    @Test
    public void testHouseNumberOK(){
        Assert.assertTrue(testSeminar.setHouseNumber("1"));
        Assert.assertTrue(testSeminar.setHouseNumber("12"));
        Assert.assertTrue(testSeminar.setHouseNumber("1232353241234353"));
        Assert.assertTrue(testSeminar.setHouseNumber("1A"));
        Assert.assertTrue(testSeminar.setHouseNumber("1a"));
    }
    @Test
    public void testHouseNumberNOK(){
        Assert.assertFalse(testSeminar.setHouseNumber(""));
        Assert.assertFalse(testSeminar.setHouseNumber("123s123"));
        Assert.assertFalse(testSeminar.setHouseNumber(null));
        Assert.assertFalse(testSeminar.setHouseNumber("   "));
    }

    //test for PLZ
    @Test
    public void testPlzOK(){
        Assert.assertTrue(testSeminar.setPlz(3132));
        Assert.assertTrue(testSeminar.setPlz(9999));
        Assert.assertTrue(testSeminar.setPlz(1000));
        Assert.assertTrue(testSeminar.setPlz(100000));
        Assert.assertTrue(testSeminar.setPlz(123456));
        Assert.assertTrue(testSeminar.setPlz(999999));
    }
    @Test
    public void testPlzNOK(){
        Assert.assertFalse(testSeminar.setPlz(0));
        Assert.assertFalse(testSeminar.setPlz(null));
        Assert.assertFalse(testSeminar.setPlz(12));
        Assert.assertFalse(testSeminar.setPlz(1221314115));testSeminar.setPlz(0);
    }


    //test for location
    @Test
    public void testLocationOK(){
        Assert.assertTrue(testSeminar.setLocation("Location mit Ümläütén"));
        Assert.assertTrue(testSeminar.setLocation("Locati0n mit Z4hl3n"));
        Assert.assertTrue(testSeminar.setLocation("Dies ist ein sehr sehr langer Locationnname denn es muss getestet werden ob auch sehr sehr lange Locationnnamen funktionieren"));
        Assert.assertTrue(testSeminar.setLocation("Abkürzungsloc."));
        Assert.assertTrue(testSeminar.setLocation("Ey"));

    }
    @Test
    public void testLocationNOK(){
        Assert.assertFalse(testSeminar.setLocation(""));
        Assert.assertFalse(testSeminar.setLocation(null));
        Assert.assertFalse(testSeminar.setLocation("      "));
        Assert.assertFalse(testSeminar.setLocation("A"));
    }


    //test for title
    @Test
    public void testTitleOK(){
        Assert.assertTrue(testSeminar.setTitle("Title mit Ümläütén"));
        Assert.assertTrue(testSeminar.setTitle("Title mit Z4hl3n"));
        Assert.assertTrue(testSeminar.setTitle("Dies ist ein sehr sehr langer Titlenname denn es muss getestet werden ob auch sehr sehr lange Titlennamen funktionieren"));
        Assert.assertTrue(testSeminar.setTitle("Abkürzungstitle."));
        Assert.assertTrue(testSeminar.setTitle("Ey"));

    }
    @Test
    public void testTitleNOK(){
        Assert.assertFalse(testSeminar.setTitle(""));
        Assert.assertFalse(testSeminar.setTitle(null));
        Assert.assertFalse(testSeminar.setTitle("      "));
        Assert.assertFalse(testSeminar.setTitle("A"));
    }


    //test for date
    @Test
    public void testDateOK(){
        Assert.assertTrue(testSeminar.setDate(LocalDateTime.now().plusDays(1)));
        Assert.assertTrue(testSeminar.setDate(LocalDateTime.now().plusMonths(1)));
        Assert.assertTrue(testSeminar.setDate(LocalDateTime.now().plusHours(1)));
        Assert.assertTrue(testSeminar.setDate(LocalDateTime.now().plusMinutes(1)));
        Assert.assertTrue(testSeminar.setDate(LocalDateTime.now().plusYears(4)));
    }

    @Test
    public void testDateNOK(){
        Assert.assertFalse(testSeminar.setDate(LocalDateTime.now().minusMinutes(1)));
        Assert.assertFalse(testSeminar.setDate(LocalDateTime.now().plusYears(6)));
    }


    //test for category
    @Test
    public void testCategoryOK(){
        Assert.assertTrue(testSeminar.setCategory(new SeminarCategory("test")));

    }

    @Test
    public void testCategoryNOK(){
        Assert.assertFalse(testSeminar.setCategory(null));
    }

    //test for url
    @Test
    public void testUrlOK(){
        Assert.assertTrue(testSeminar.setUrl("https://docs.jboss.org/hibernate/beanvalidation/spec/2.0/api/javax/validation/constraints/NotBlank.html"));
        Assert.assertTrue(testSeminar.setUrl("https://www.vogella.com/tutorials/JUnit/article.html"));
        Assert.assertTrue(testSeminar.setUrl("www.google.ch"));
        Assert.assertTrue(testSeminar.setUrl("http://test.org"));
    }
    @Test
    public void testUrlNOK(){
        Assert.assertFalse(testSeminar.setUrl(""));
        Assert.assertFalse(testSeminar.setUrl(null));
        Assert.assertFalse(testSeminar.setUrl("   "));
        Assert.assertFalse(testSeminar.setUrl("A"));
        Assert.assertFalse(testSeminar.setUrl("www.göögle.com"));
    }


    //test for description
    @Test
    public void testDescriptionOK(){
        Assert.assertTrue(testSeminar.setDescription("Description mit Ümläütén"));
        Assert.assertTrue(testSeminar.setDescription("Description mit Z4hl3n"));
        Assert.assertTrue(testSeminar.setDescription("Dies ist ein sehr sehr langer Descriptionnname denn es muss getestet werden ob auch sehr sehr lange Descriptionnnamen funktionieren"));
        Assert.assertTrue(testSeminar.setDescription("AbkürzungsDescription."));
        Assert.assertTrue(testSeminar.setDescription("Ey"));

    }
    @Test
    public void testDescriptionNOK(){
        Assert.assertFalse(testSeminar.setDescription(""));
        Assert.assertFalse(testSeminar.setDescription(null));
        Assert.assertFalse(testSeminar.setDescription("      "));
        Assert.assertFalse(testSeminar.setDescription("A"));
    }
}
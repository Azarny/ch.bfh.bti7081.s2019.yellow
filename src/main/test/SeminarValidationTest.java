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
    Seminar testSeminar= new Seminar();

    // tests for street
    @Test
    public void testStreetOK(){
        testSeminar.setStreet("Strasse mit Ümläütén");
        testSeminar.setStreet("Str4sse mit Z4hl3n");
        testSeminar.setStreet("Dies ist ein sehr sehr langer Strassenname denn es muss getestet werden ob auch sehr sehr lange Strassennamen funktionieren");
        testSeminar.setStreet("Abkürzungsstr.");
        testSeminar.setStreet("Ey");
    }
    @Test(expected = IllegalArgumentException.class    )
    public void testStreetEmpty(){
        testSeminar.setStreet("");
    }

    @Test(expected = NullPointerException.class    )
    public void testStreetNull(){
        testSeminar.setStreet(null);
    }

    @Test(expected = IllegalArgumentException.class    )
    public void testStreetBlank(){
        testSeminar.setStreet("   ");
    }

    @Test(expected = IllegalArgumentException.class    )
    public void testStreetShort(){
        testSeminar.setStreet("A");
    }


    //tests for houseNumber
    @Test
    public void testHouseNumberOK(){
        testSeminar.setHouseNumber("1");
        testSeminar.setHouseNumber("12");
        testSeminar.setHouseNumber("1232353241234353");
        testSeminar.setHouseNumber("1A");
        testSeminar.setHouseNumber("1a");
    }
    @Test(expected = IllegalArgumentException.class    )
    public void testHouseNumberEmpty(){
        testSeminar.setHouseNumber("");
    }

    @Test(expected = IllegalArgumentException.class    )
    public void testHouseNumberNOK(){
        testSeminar.setHouseNumber("123s123");
    }

    @Test(expected = NullPointerException.class    )
    public void testHouseNumberNull(){
        testSeminar.setHouseNumber(null);
    }

    @Test(expected = IllegalArgumentException.class    )
    public void testHouseNumberBlank(){
        testSeminar.setHouseNumber("   ");
    }


    //test for PLZ
    @Test
    public void testPlzOK(){
        testSeminar.setPlz(3132);
        testSeminar.setPlz(9999);
        testSeminar.setPlz(1000);
        testSeminar.setPlz(100000);
        testSeminar.setPlz(123456);
        testSeminar.setPlz(999999);
    }
    @Test(expected = IllegalArgumentException.class    )
    public void testHousePlzEmpty(){
        testSeminar.setPlz(0);
    }
    @Test(expected = IllegalArgumentException.class    )
    public void testPlzNull(){
        testSeminar.setPlz(null);
    }
    @Test(expected = IllegalArgumentException.class    )
    public void testPlzNOK(){
        testSeminar.setPlz(12);
    }
    @Test(expected = IllegalArgumentException.class    )
    public void testPlzNOK2(){
        testSeminar.setPlz(1221314115);
    }


    //test for location
    @Test
    public void testLocationOK(){
        testSeminar.setLocation("Location mit Ümläütén");
        testSeminar.setLocation("L0cati0n mit Z4hl3n");
        testSeminar.setLocation("Dies ist ein sehr sehr langer LocationName denn es muss getestet werden ob auch sehr sehr lange Locationnamen funktionieren");
        testSeminar.setLocation("Abkürz. Loc.");
        testSeminar.setLocation("Ey");
    }
    @Test(expected = IllegalArgumentException.class    )
    public void testLocationEmpty(){
        testSeminar.setLocation("");
    }

    @Test(expected = NullPointerException.class    )
    public void testLocationNull(){
        testSeminar.setLocation(null);
    }

    @Test(expected = IllegalArgumentException.class    )
    public void testLocationBlank(){
        testSeminar.setLocation("   ");
    }

    @Test(expected = IllegalArgumentException.class    )
    public void testLocationShort(){
        testSeminar.setLocation("A");
    }


    //test for title
    @Test
    public void testTitleOK(){
        testSeminar.setTitle("Titel mit Ümläütén");
        testSeminar.setTitle("T1t3l mit Z4hl3n");
        testSeminar.setTitle("Dies ist ein sehr sehr langer Titelname denn es muss getestet werden ob auch sehr sehr lange Titelnamen funktionieren");
        testSeminar.setTitle("Tit. Kt.");
        testSeminar.setTitle("Ey");
    }
    @Test(expected = IllegalArgumentException.class    )
    public void testTitleEmpty(){
        testSeminar.setTitle("");
    }

    @Test(expected = NullPointerException.class    )
    public void testTitleNull(){
        testSeminar.setTitle(null);
    }

    @Test(expected = IllegalArgumentException.class    )
    public void testTitleBlank(){
        testSeminar.setTitle("   ");
    }

    @Test(expected = IllegalArgumentException.class    )
    public void testTitleShort(){
        testSeminar.setTitle("A");
    }


    //test for date
    @Test
    public void testDateOK(){
        testSeminar.setDate(LocalDateTime.now().plusDays(1));
        testSeminar.setDate(LocalDateTime.now().plusMonths(1));
        testSeminar.setDate(LocalDateTime.now().plusHours(1));
        testSeminar.setDate(LocalDateTime.now().plusMinutes(1));
        testSeminar.setDate(LocalDateTime.now().plusYears(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDatePast(){
        testSeminar.setDate(LocalDateTime.now().minusMinutes(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDateFuture(){
        testSeminar.setDate(LocalDateTime.now().plusYears(6));
    }

    //test for category
    @Test
    public void testCategoryOK(){
        testSeminar.setCategory(new SeminarCategory("test"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCategoryNOK(){
        testSeminar.setCategory(null);
    }

    //test for url
    @Test
    public void testUrlOK(){
        testSeminar.setUrl("https://docs.jboss.org/hibernate/beanvalidation/spec/2.0/api/javax/validation/constraints/NotBlank.html");
        testSeminar.setUrl("https://www.vogella.com/tutorials/JUnit/article.html");
        testSeminar.setUrl("www.google.ch");
        testSeminar.setUrl("http://test.org");
    }
    @Test(expected = IllegalArgumentException.class    )
    public void testUrlEmpty(){
        testSeminar.setUrl("");
    }

    @Test(expected = NullPointerException.class    )
    public void testUrlNull(){
        testSeminar.setUrl(null);
    }

    @Test(expected = IllegalArgumentException.class    )
    public void testUrlBlank(){
        testSeminar.setUrl("   ");
    }

    @Test(expected = IllegalArgumentException.class    )
    public void testUrlShort(){
        testSeminar.setUrl("A");
    }

    @Test(expected = IllegalArgumentException.class    )
    public void testUrlUmlaute(){
        testSeminar.setUrl("www.göögle.com");
    }


    //test for description
    @Test
    public void testDescriptionOK(){
        testSeminar.setDescription("Description mit Ümläütén");
        testSeminar.setDescription("D3scription mit Z4hl3n");
        testSeminar.setDescription("Dies ist ein sehr sehr langer Strassenname denn es muss getestet werden ob auch sehr sehr lange Strassennamen funktionieren");
        testSeminar.setDescription("Abkürzungsstr.");
        testSeminar.setDescription("Ey");
    }
    @Test(expected = IllegalArgumentException.class    )
    public void testDescriptionEmpty(){
        testSeminar.setDescription("");
    }

    @Test(expected = NullPointerException.class    )
    public void testDescriptionNull(){
        testSeminar.setDescription(null);
    }

    @Test(expected = IllegalArgumentException.class    )
    public void testDescriptionBlank(){
        testSeminar.setDescription("   ");
    }

    @Test(expected = IllegalArgumentException.class    )
    public void testDescriptionShort(){
        testSeminar.setDescription("A");
    }
}
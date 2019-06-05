import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.manager.UserManager;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class MockTest {

    UserManager manager = new UserManager();

    @Test
    public void UserTester(){
        Assert.assertEquals(4,(long)manager.getUserByUsername("Admin").getPermission());
    }

    @Test
    public void SeminarCategoryTester(){
        SeminarCategoryManager seminarCategoryManager = new SeminarCategoryManager();
        List<SeminarCategory> test = seminarCategoryManager.getSeminarCategories();
        Assert.assertEquals("Allgemeines",test.get(3).getName());
    }

    @Test
    public void SeminarTester(){
        SeminarManager seminarManager = new SeminarManager();
        Assert.assertEquals(4,seminarManager.getSeminaries().size());
    }

}

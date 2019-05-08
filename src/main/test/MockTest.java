import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.UserManager;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class MockTest {
    @Test
    public void UserTester(){
        Assert.assertEquals(4,(long)UserManager.getUserByUsername("Admin").getPermission());
    }

    @Test
    public void SeminarCategoryTester(){
        List<SeminarCategory> test = SeminarCategoryManager.getSeminarCategories();
        Assert.assertEquals("Allgemeines",test.get(3).getName());
    }
}

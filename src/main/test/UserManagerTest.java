import ch.bfh.bti7081.App;
import ch.bfh.bti7081.model.manager.UserManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UserManagerTest {

    @Autowired
    private UserManager manager;

    @Test
    public void encryption() throws Exception {
        String salt = UserManager.generateSalt().toString();
        String password = "Test1234xy>";
        Assert.assertEquals(manager.generateHash(password, salt), manager.generateHash(password,salt));
    }
}

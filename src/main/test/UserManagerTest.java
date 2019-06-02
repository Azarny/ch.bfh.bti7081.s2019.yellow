import ch.bfh.bti7081.model.manager.UserManager;
import org.junit.Assert;
import org.junit.Test;

public class UserManagerTest {

    private UserManager manager = new UserManager();

    @Test
    public void encryption() throws Exception {
        String salt = UserManager.generateSalt().toString();
        String password = "Test1234xy>";
        Assert.assertEquals(manager.generateHash(password, salt), manager.generateHash(password,salt));
    }
}

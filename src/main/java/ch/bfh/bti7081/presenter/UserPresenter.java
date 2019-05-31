package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.User;
import ch.bfh.bti7081.model.dto.UserDTO;
import ch.bfh.bti7081.model.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Component
public class UserPresenter {

    @Autowired
    UserManager manager;

    private UserDTO convertModeltoDTO(User user){
        if (user == null){
            return null;
        }
        UserDTO dtoObject = new UserDTO();
        dtoObject.setUsername(user.getUsername());
        dtoObject.setEncryptedPassword(user.getPassword());
        dtoObject.setComments(user.getComments());
        dtoObject.setEmail(user.getEmail());
        dtoObject.setForumEntries(user.getForumEntries());
        dtoObject.setPermission(user.getPermission());

        return dtoObject;
    }

    public UserDTO getUserByUsername(String username) {
        return convertModeltoDTO(manager.getUserByUsername(username));
    }

    public String encryptPassword(UserDTO user, String password) throws Exception {
        // check if user exists in database
        if (null != getUserByUsername(user.getUsername())){
             return manager.generateHash(password, user.getSalt());
        }

        String salt = UserManager.generateSalt(UserManager.KEY_LENGTH).toString();
        user.setSalt(salt);
        return manager.generateHash(password, salt);
    }
}

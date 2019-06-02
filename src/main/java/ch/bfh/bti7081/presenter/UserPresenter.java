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

    /*
     * converts a model to a data transfer object
     *
     * Author: luscm1
     * */
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
        dtoObject.setSalt(user.getSalt());

        return dtoObject;
    }

    public UserDTO getUserByUsername(String username) {
        return convertModeltoDTO(manager.getUserByUsername(username));
    }

    /*
     * encrypts a password
     * only existing users are currently allowed
     *
     * Author: luscm1
     * */
    public String encryptPassword(String username, String password) throws Exception {
        // check if user exists in database
        UserDTO user = getUserByUsername(username);
        if (null != user){
             return manager.generateHash(password, user.getSalt());
        }

        throw new Exception("The user " + username + "does not exists.");
    }
}

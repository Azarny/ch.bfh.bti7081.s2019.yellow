package ch.bfh.bti7081.presenter;

import ch.bfh.bti7081.model.User;
import ch.bfh.bti7081.model.dto.UserDTO;
import ch.bfh.bti7081.model.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPresenter {

    @Autowired
    UserManager manager;
    private User convertDTOtoModel(UserDTO userDTO) {
        User modelObject = new User();
        modelObject.setUsername(userDTO.getUsername());
        modelObject.setPassword(manager.encryptPassword(userDTO.getPassword()));
        modelObject.setComments(userDTO.getComments());
        modelObject.setEmail(userDTO.getEmail());
        modelObject.setForumEntries(userDTO.getForumEntries());
        modelObject.setPermission(userDTO.getPermission());
        return modelObject;
    }

    private UserDTO convertModeltoDTO(User user) {
        if (user == null){
            return null;
        }
        UserDTO dtoObject = new UserDTO();
        dtoObject.setUsername(user.getUsername());
        dtoObject.setPassword(user.getPassword());
        dtoObject.setComments(user.getComments());
        dtoObject.setEmail(user.getEmail());
        dtoObject.setForumEntries(user.getForumEntries());
        dtoObject.setPermission(user.getPermission());

        return dtoObject;
    }

    public UserDTO getUserByUsername(String username){
        return convertModeltoDTO(manager.getUserByUsername(username));
    }
}

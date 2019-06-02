package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.model.User;
import ch.bfh.bti7081.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserManager {

    @Autowired
    UserRepository userRepository;

    public User getUserByUsername(String name) {
        Optional<User> user = userRepository.findAll().stream().filter(u -> u.getUsername().equals(name)).findAny();
        if (user.isPresent()) {
            return user.get();
        } else {
            return null;
        }

    }

    public void createUser(User user){
        userRepository.save(user);
    }

    public String encryptPassword(String password){
        return "Not implemented yet";
    }

}

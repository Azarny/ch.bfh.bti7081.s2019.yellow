package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserManager {
    public static User getUserByUsername(String name) {
        Optional<User> user = getMockUsers().stream().filter(u -> u.getUsername().equals(name)).findAny();
        if (user.isPresent()) {
            return user.get();
        } else {
            return null;
        }

    }

    private static List<User> getMockUsers() {
        //These is mock data, nothing here is productive code.
        List<User> mockUsers = new ArrayList<>();
        User mockUser1 = new User();
        mockUser1.setEmail("nomail@nomailhausen.com");
        mockUser1.setPassword("12345");
        mockUser1.setPermission(1);
        mockUser1.setUsername("Lara");
        mockUsers.add(mockUser1);
        User mockUser2 = new User();
        mockUser2.setEmail("nomail@nomailhausen.com");
        mockUser2.setPassword("12345");
        mockUser2.setPermission(4);
        mockUser2.setUsername("Admin");
        mockUsers.add(mockUser2);
        return mockUsers;
    }


}

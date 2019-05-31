package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.model.User;
import ch.bfh.bti7081.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import static java.util.Arrays.*;

@Component
public class UserManager {

    @Autowired
    UserRepository userRepository;

    // some variables and constants for the encryption
    private static final SecureRandom RAND = new SecureRandom();
    private static final int ITERATIONS = 65536;
    public static final int KEY_LENGTH = 512;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";

    public User getUserByUsername(String name) {
        Optional<User> user = userRepository.findAll().stream().filter(u -> u.getUsername().equals(name)).findAny();
        return user.orElse(null);
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    // this function is used from https://dev.to/awwsmm/how-to-encrypt-a-password-in-java-42dh
    public static Optional<String> generateSalt (final int length) {

        if (length < 1) {
            System.err.println("error in generateSalt: length must be > 0");
            return Optional.empty();
        }

        byte[] salt = new byte[length];
        RAND.nextBytes(salt);

        return Optional.of(Base64.getEncoder().encodeToString(salt));
    }

    // this function is used from https://dev.to/awwsmm/how-to-encrypt-a-password-in-java-42dh
    public String generateHash (String password, String salt) throws Exception {
        char[] chars = password.toCharArray();
        byte[] bytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);

        fill(chars, Character.MIN_VALUE);

        try {
            SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] securePassword = fac.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(securePassword);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new Exception("Exception encountered in hashPassword()");
        } finally {
            spec.clearPassword();
        }
    }

}

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
import java.util.Base64;
import java.util.Optional;

import static java.util.Arrays.fill;

/**
 * @author siegn2
 */
@Component
public class UserManager {

    @Autowired
    UserRepository userRepository;

    // some variables and constants for the encryption
    private static final SecureRandom RAND = new SecureRandom();
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 512;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";

    /**
     * Returns user with username from DB
     * @param name Username
     * @return User
     * @author siegn2
     */
    public User getUserByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    /*
     * generates a random salt.
     *this function is copied from https://dev.to/awwsmm/how-to-encrypt-a-password-in-java-42dh
     *
     * Author: Andrew (https://dev.to/awwsmm)
     * */
    public static Optional<String> generateSalt () {
        int length = 128;

        byte[] salt = new byte[length];
        RAND.nextBytes(salt);

        return Optional.of(Base64.getEncoder().encodeToString(salt));
    }

    /*
     * creates a hash
     *this function is copied from https://dev.to/awwsmm/how-to-encrypt-a-password-in-java-42dh
     *
     * Author: Andrew (https://dev.to/awwsmm)
     * */
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

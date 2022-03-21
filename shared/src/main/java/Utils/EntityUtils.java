package Utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;

public class EntityUtils {

    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 128;
    private static final String SECRET = "PBKDF2WithHmacSHA1";

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static Map<String, String> hashPassword(String password) {
        Map<String, String> pair = new HashMap<>();

        try {
            byte[] salt = new byte[16];
            Random random = new Random();
            random.nextBytes(salt);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRET);
            byte[] hash = keyFactory.generateSecret(spec).getEncoded();
            pair.put(Base64.getEncoder().encodeToString(salt), Base64.getEncoder().encodeToString(hash));
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            System.out.println("Error hashing password due to following exception: " + ex);
            pair.put(null, password);
        }

        return pair;
    }

    public static boolean verifyPassword(String password, String passwordHash, String passwordSalt) {
        try {
            byte[] salt = Base64.getDecoder().decode(passwordSalt);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRET);
            byte[] hash = keyFactory.generateSecret(spec).getEncoded();
            return passwordHash.equals(Base64.getEncoder().encodeToString(hash));
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            System.out.println("Error verifying password due to following exception: " + ex);
        }

        return false;
    }

    public static boolean idAIsLessThanIdB(String idA, String idB) {
        List<String> strings = new ArrayList<>();
        strings.add(idA);
        strings.add(idB);
        strings.sort(String.CASE_INSENSITIVE_ORDER);
        return strings.get(0).equals(idA);
    }

    public static boolean checkNullableObjects(Object a, Object b) {
        try {
            return a.equals(b);
        }
        catch (NullPointerException ex) {
            return a == null && b == null;
        }
    }
}
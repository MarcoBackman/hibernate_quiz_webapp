package com.example.week3day13project.security;

import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/**
 * After salting and hashing a user password during the registration,
 * we must also store the salt along with the hashed password in the database.
 */

@Slf4j
public class PasswordHandler {

    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public static String getSalt(int length) {
        byte[] salt = new byte[length];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) {
        char[] chars = password.toCharArray();
        byte[] bytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);

        Arrays.fill(chars, Character.MIN_VALUE);

        try {
            SecretKeyFactory fac = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] securePassword = fac.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(securePassword);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            log.error("Exception encountered in hashPassword()", ex);
            return null;

        } finally {
            spec.clearPassword();
        }
    }

    public static boolean isValidUserPassword(String userInputPassword, String storedHashedPassword, String storedSalt) {
        String hashedUserInputPassword = hashPassword(userInputPassword, storedSalt);
        if (hashedUserInputPassword == null || hashedUserInputPassword.isEmpty()) {
            log.warn("hash calculation returned empty value");
            return false;
        }
        //log.debug("Stored password={}, hashedPassword={}", storedHashedPassword, hashedUserInputPassword);
        //log.debug(hashedUserInputPassword);
        return hashedUserInputPassword.equals(storedHashedPassword);
    }
}

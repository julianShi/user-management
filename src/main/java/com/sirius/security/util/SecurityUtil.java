package com.sirius.security.util;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * java security is not allowed in this project. So I created this utility class for the encryption.
 */
public class SecurityUtil {
    public static String encryptPassword(String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // Hard coded a key
            SecretKey secretKey = new SecretKeySpec("soAfIHEVrpA0qk4L1swblg==".getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new InternalException("Failed to create new user");
        }
    }
}

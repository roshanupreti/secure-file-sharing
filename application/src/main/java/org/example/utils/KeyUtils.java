package org.example.utils;

import org.example.exception.RESTException;
import org.springframework.http.HttpStatus;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

public final class KeyUtils {

    private KeyUtils() {
    }

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, loadSecretKey());
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeyException e) {
            throw new RESTException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public static String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, loadSecretKey());
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException |
                 InvalidKeyException e) {
            throw new RESTException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private static SecretKey loadSecretKey() {
        try {
            // Use SHA-256 to hash the string and get bytes
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] key = sha.digest("5155123931".getBytes());
            // Use only the first 16 bytes (128 bits) for AES key
            key = Arrays.copyOf(key, 16);
            return new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RESTException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}


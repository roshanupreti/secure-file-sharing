package org.example.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.exception.RESTException;
import org.junit.jupiter.api.Test;

class KeyUtilsTest {

    /**
     * Method under test: {@link KeyUtils#encrypt(String)}
     */
    @Test
    void encrypt() {
        // Arrange, Act and Assert
        assertEquals("UZokP/1JgB+XAdvuY02Vrw==", KeyUtils.encrypt("Data"));
    }

    /**
     * Method under test: {@link KeyUtils#decrypt(String)}
     */
    @Test
    void decrypt() {
        // Arrange, Act and Assert
        assertThrows(RESTException.class, () -> KeyUtils.decrypt("AES"));
        assertEquals("", KeyUtils.decrypt(""));
    }
}

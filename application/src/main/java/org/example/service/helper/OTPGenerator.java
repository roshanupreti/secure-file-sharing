package org.example.service.helper;

import java.security.SecureRandom;

public final class OTPGenerator {

    private OTPGenerator() {
    }

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = UPPER.toLowerCase();
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+";
    private static final Integer LENGTH = 6;

    public static String generateOTP() {
        String characters = UPPER + LOWER + DIGITS + SPECIAL_CHARACTERS;

        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            otp.append(characters.charAt(random.nextInt(characters.length())));
        }

        return otp.toString();
    }
}

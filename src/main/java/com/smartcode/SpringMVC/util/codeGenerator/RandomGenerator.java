package com.smartcode.SpringMVC.util.codeGenerator;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class RandomGenerator {

    private static final String NUMERIC = "0123456789";
    private static final SecureRandom rnd = new SecureRandom();

    public static String generateNumericString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(NUMERIC.charAt(rnd.nextInt(NUMERIC.length())));
        }
        return sb.toString();
    }
}
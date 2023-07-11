package com.smartcode.SpringMVC.util.encoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encoder {

    private static final MessageDigest m;

    static {
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encode(String text) {
        m.reset();
        m.update(text.getBytes());
        var digest = m.digest();
        var bigInt = new BigInteger(1, digest);
        return bigInt.toString(16);
    }

}

package com.smartcode.SpringMVC.util.encoder;


import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESManager {

    public final static int GCM_TAG_LENGTH = 16;
    public final static int GCM_IV_LENGTH = 12;
    private final static String secret = "UGCCkJ4+9ppyeCs54hHk72YpfeaulaopNdnwScyKu24=";
    private final static byte[] IV = new byte[GCM_IV_LENGTH];


    public static String encrypt(String plaintext) {
        try {
            byte[] secretBytes = Base64.getDecoder().decode(secret);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(secretBytes, "AES");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);
            byte[] cipherText = cipher.doFinal(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            throw new RuntimeException("Encoding failed!");
        }
    }

    public static String decrypt(String cipherText) {
        try {
            byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText);
            byte[] secretBytes = Base64.getDecoder().decode(secret);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(secretBytes, "AES");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
            byte[] decryptedText = cipher.doFinal(cipherTextBytes);
            return new String(decryptedText);
        } catch (Exception e) {
            throw new RuntimeException("Decoding failed!");
        }
    }
}

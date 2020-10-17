package com.clarisight.task.demo;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import org.bouncycastle.util.encoders.Base64;



@Slf4j
public class ClariSightUtils {
    public static String generateHash(String original){
        String encrypted = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(original.getBytes("UTF-8"));
            byte raw[] = md.digest();
            encrypted = new String(Base64.encode(raw));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return encrypted;
    }
}

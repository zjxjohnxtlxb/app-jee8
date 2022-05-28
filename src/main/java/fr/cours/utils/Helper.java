package fr.cours.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class Helper {

    private static Key jwtKey;

    public static String hashUtil(String password) {
        try {
            return new String(Base64.getEncoder().encode(MessageDigest.getInstance("SHA-256").digest(password.getBytes(StandardCharsets.ISO_8859_1))));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static Key getJwtKey() {
        if (jwtKey == null)
            jwtKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return jwtKey;
    }
}

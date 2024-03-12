package edu.escuelaing.arem.ase.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cypher {
    public static String cypherPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA3-256");

        byte[] hash = md.digest(password.getBytes());

        StringBuffer sb = new StringBuffer();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static boolean validatePassword(String passToValidate, String password) throws NoSuchAlgorithmException {
        return password.equals(Cypher.cypherPassword(passToValidate));
    }
}

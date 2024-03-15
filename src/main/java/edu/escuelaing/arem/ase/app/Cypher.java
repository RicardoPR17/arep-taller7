package edu.escuelaing.arem.ase.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cypher {

    /**
     * Get a password and cypher it with the SHA3-256 algorithm
     * 
     * @param password The password to be cyphred
     * @return A String representing the cyphred password
     * @throws NoSuchAlgorithmException If the cypher algorith does not exist
     */
    public static String cypherPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA3-256");

        byte[] hash = md.digest(password.getBytes());

        StringBuffer sb = new StringBuffer();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    /**
     * Check if a given password is correct with the user's known cyphred one. This
     * comparison made between the hash version of both passwords.
     * 
     * @param passToValidate  The password entered by the user
     * @param correctPassword The correct password of the user
     * @return True if the password match, False otherwise.
     * @throws NoSuchAlgorithmException
     */
    public static boolean validatePassword(String passToValidate, String correctPassword)
            throws NoSuchAlgorithmException {
        System.out.println(Cypher.cypherPassword(passToValidate));
        return correctPassword.equals(Cypher.cypherPassword(passToValidate));
    }
}

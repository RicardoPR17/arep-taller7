package edu.escuelaing.arem.ase.app;

import static spark.Spark.secure;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserDB {
    private static Map<String, String> db = new HashMap<>();

    public static void main(String[] args) throws NoSuchAlgorithmException {
        db.put("Ricardo", Cypher.cypherPassword("17ricardo09"));
        secure("certificados/ecikeystore.p12", "123456", null, null);
    }
}

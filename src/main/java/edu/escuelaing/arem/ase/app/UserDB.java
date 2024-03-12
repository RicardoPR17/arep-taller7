package edu.escuelaing.arem.ase.app;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.secure;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserDB {
    private static Map<String, String> db = new HashMap<>();

    public static void main(String[] args) throws NoSuchAlgorithmException {
        db.put("Ricardo", Cypher.cypherPassword("17ricardo09"));
        db.put("Recker", Cypher.cypherPassword("battlefield4"));
        secure("certificados/ecikeystore.p12", "123456", null, null);
        port(getPort());
        get("/login", (req, res) -> {
            String username = req.queryParams("user");
            String password = req.queryParams("pass");

            if (!db.containsKey(username)) {
                return "User not found";
            }

            return Cypher.validatePassword(password, db.get(username)) ? "Login successfully" : "Incorrect password";
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5005;
    }
}

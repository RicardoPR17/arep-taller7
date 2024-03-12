package edu.escuelaing.arem.ase.app;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.secure;
import static spark.Spark.staticFiles;

public class HelloService {
    public static void main(String[] args) {
        secure("certificados/ecikeystore.p12", "123456", null, null);
        port(getPort());
        staticFiles.location("/public");
        get("/hello", (req, res) -> "Hello World");
        post("/login", (req, res) -> {
            String username = req.queryParams("user");
            String password = req.queryParams("pass");

            return SecureUrlReader.secureReadUrl(username, password);
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
    }
}
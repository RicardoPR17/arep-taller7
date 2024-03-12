package edu.escuelaing.arem.ase.app;

import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SecureUrlReader {

    public static String secureReadUrl(String username, String password) {
        String secureResponse = "";
        try {
            // Create a file and a password representation
            File trustStoreFile = new File("certificados/myTrustStore.p12");
            char[] trustStorePassword = "123456".toCharArray();

            // Load the trust store, the default type is "pkcs12", the alternative is "jks"
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);

            // Get the singleton instance of the TrustManagerFactory
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());

            // Itit the TrustManagerFactory using the truststore object
            tmf.init(trustStore);

            // Print the trustManagers returned by the TMF
            // only for debugging
            for (TrustManager t : tmf.getTrustManagers()) {
                System.out.println(t);
            }

            // Set the default global SSLContext so all the connections will use it
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);

            // We can now read this URL
            secureResponse = readURL("https://localhost:5005/login?user=" + username + "&pass=" + password);

            // // This one can't be read because the Java default truststore has been
            // // changed.
            // readURL("https://www.google.com");

        } catch (KeyStoreException ex) {
            Logger.getLogger(SecureUrlReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SecureUrlReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SecureUrlReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SecureUrlReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(SecureUrlReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(SecureUrlReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return secureResponse;
    }

    public static String readURL(String sitetoread) {
        StringBuffer response = new StringBuffer();
        try {
            // Crea el objeto que representa una URL2
            URL siteURL = new URL(sitetoread);
            // Crea el objeto que URLConnection
            URLConnection urlConnection = siteURL.openConnection();

            System.out.println("-------message-body------");
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                response.append(inputLine);
            }
        } catch (IOException x) {
            System.out.println("aiuda");
            response.append(x);
        }
        return response.toString();
    }
}
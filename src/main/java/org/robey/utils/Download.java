package org.robey.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.time.StopWatch;

import static org.robey.utils.Log.log;

public class Download {

    // if cert is good use this method
    public static void downloadFile(String sourceURL, File outFile) throws Exception {
        if(outFile.exists()) return;
        log("Downloading URL: " + sourceURL + " to: " + outFile.getName());
        try(final InputStream is = new URL(sourceURL).openStream()) {
            final String contents = new String(is.readAllBytes());
            try(final FileOutputStream fos = new FileOutputStream(outFile)) {
                fos.write(contents.getBytes());
            }
        }
    }

    // disables all cert validation
    public static void download(String sourceURL, String outFile) throws Exception {
        File file = new File(outFile);
        if(file.canRead()) return;
        log("Downloading URL: " + sourceURL + " to: " + outFile);
        StopWatch watch = StopWatch.createStarted();

        // Create a trust manager that does not validate certificate chains
        final TrustManager[] trustAllCerts = {
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {return null;}
                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        // Install the all-trusting trust manager
        final SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create an all-trusting host name verifier
        final HostnameVerifier allHostsValid = (hostname, session) -> true;

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        // Open connection to the file URL
        final URL url = new URL(sourceURL);
        final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        // Download the file
        try (final InputStream in = new BufferedInputStream(connection.getInputStream());
             final FileOutputStream out = new FileOutputStream(outFile)) {
            final byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        watch.stop();
        log("Took: " + watch.formatTime() + " to download file size: " + file.length());
    }
}

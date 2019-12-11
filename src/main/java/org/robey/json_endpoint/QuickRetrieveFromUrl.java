package org.robey.json_endpoint;

/**
 * See: https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
 */
public class QuickRetrieveFromUrl {
    public static void main(String args[]) throws Exception {
        try(java.io.InputStream is = new java.net.URL("https://api.chucknorris.io/jokes/categories").openStream()) {
            String contents = new String(is.readAllBytes());
            System.out.println(contents);
            com.google.gson.JsonElement jp = com.google.gson.JsonParser.parseString(contents); //from gson
        }

    }
}

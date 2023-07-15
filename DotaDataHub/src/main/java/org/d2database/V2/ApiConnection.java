package org.d2database.V2;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ApiConnection {
    String apiKey;
    String endPoint;
    String methodCall;
    String baseURL;
    String urlPath;
    HttpsURLConnection connection;
    URL url;
    StringBuilder json;
    // Order methods in order of invocation
    public ApiConnection(String endPoint, String methodCall) {
        //-----------------------Initialize-Variables------------------------
        this.endPoint = endPoint;
        this.apiKey = Data.API_KEY.getData();
        this.methodCall = methodCall;
        this.baseURL = Data.BASE_URL.getData();
        this.urlPath = this.baseURL + this.endPoint;
        //------------------Initialize-Connection-Variables-------------------
        try {
            this.url = new URL(this.urlPath);     // URL
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        try {                                     // Connection
            this.connection = (HttpsURLConnection) this.url.openConnection();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //-----------------------Invoke-Connection----------------------------
        this.invokeConnection();
    }
    //-----------------------Invoke-Connection-Method-------------------------
    public void invokeConnection() {
        this.buildConnection();
        this.connect();
        this.receiveJSON();
        this.disconnect();
    }
    //-----------------Initialize-Connection-Method-And-Headers---------------
    public void buildConnection() {
        this.connection.setRequestProperty("Authorization", "Bearer " + this.apiKey);
        this.connection.setRequestProperty("Content-Type", "application/json");
        this.connection.setRequestProperty("Cache-Control", "no-cache");
        this.connection.setRequestProperty("X-Content-Type-Options", "nosniff");
        this.connection.setRequestProperty("X-Frame-Options", "deny");
        this.connection.setRequestProperty("Strict-Transport-Security", "max-age=3153600");
        try {
            this.connection.setRequestMethod(this.methodCall);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    //----------------------------Connect-Method------------------------------
    public void connect() {
        try {
            this.connection.connect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    //---------------------------Receive-JSON---------------------------------
    // This method takes data from an open connection and reads it into a
    // String.
    public void receiveJSON() {
        InputStreamReader inputStreamReader = null; // General Data Input Stream
        try {                                       // Stream Reader for Connection
            inputStreamReader = new InputStreamReader(this.connection.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        StringBuilder jsonResponse = new StringBuilder();     // JSON Response
        BufferedReader connReader = null;            // Connection Text Reader
        if (inputStreamReader != null) {
            connReader = new BufferedReader(inputStreamReader);
        }
        String connResponseData;
        if (connReader != null) { // If the Text Reader is not null, read the data
            try {
                while ((connResponseData = connReader.readLine()) != null) {
                    jsonResponse.append(connResponseData);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                connReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        this.json = jsonResponse;
    }
    //-----------------------------Disconnect---------------------------------
    public void disconnect() {
        this.connection.disconnect();
    }
    //-------------------------------Getters----------------------------------
    public StringBuilder getJson() {
        return this.json;
    }
}

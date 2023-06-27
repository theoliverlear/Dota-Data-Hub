package org.d2database;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class OpenDotaConnection {
    HttpsURLConnection connection;
    URL url;
    String apiKey;
    String endPoint;
    String urlPath;
    String method;
    HashMap<String, String> requestHeaders;
    String pageData;
    public OpenDotaConnection(String apiKey, String endPoint, String method) {
        this.apiKey = apiKey;
        this.endPoint = endPoint;
        this.urlPath = OpenDotaConst.BASE_URL.getBaseUrl() + this.endPoint + "?api_key=" + this.apiKey;
        this.method = method;
        this.requestHeaders = new HashMap<>();
        this.pageData = "";
        try {
            this.url = new URL(this.urlPath);
        } catch (MalformedURLException err) {
            err.printStackTrace();
        }
        try {
            this.connection = (HttpsURLConnection) this.url.openConnection();
        } catch (IOException err) {
            err.printStackTrace();
        }
        try {
            this.connection.setRequestMethod(this.method);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        this.requestHeaders.put("Authorization", "Bearer " + this.apiKey);
        this.requestHeaders.put("Content-Type", "application/json");
        this.requestHeaders.put("Cache-Control", "no-cache");
        this.requestHeaders.put("X-Content-Type-Options", "nosniff");
        this.requestHeaders.put("X-Frame-Options", "deny");
        this.requestHeaders.put("Strict-Transport-Security", "max-age=3153600");
        for (Map.Entry<String, String> entry : this.requestHeaders.entrySet()) {
            this.connection.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }
    public void connect() {
        try {
            this.connection.connect();
        } catch (IOException err) {
            err.printStackTrace();
        }
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(this.connection.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder response = new StringBuilder();
        BufferedReader parser = new BufferedReader(inputStreamReader);
        String line;
        try {
            while ((line = parser.readLine()) != null) {
                response.append(line + "\n");
            }
        } catch (IOException err) {
            err.printStackTrace();
        }
        try {
            parser.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
        this.pageData = response.toString();
        this.connection.disconnect();
    }
    public String getPageData() {
        return this.pageData;
    }
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
    public void updateUrlPath() {
        this.urlPath = OpenDotaConst.BASE_URL.getBaseUrl() + this.endPoint + "?api_key=" + this.apiKey;
    }
    public void newURL(String endPoint) {
        this.endPoint = endPoint;
        this.updateUrlPath();
        try {
            this.url = new URL(this.urlPath);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}

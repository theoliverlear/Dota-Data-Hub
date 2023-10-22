package org.d2database.V2.executable;

import org.d2database.V2.ApiConnection;

public class ApiResponse {
    public static void main(String[] args) {
        String match = "1772444224";
        String urlPath = "matches/" + match;
        ApiConnection connection = new ApiConnection(urlPath, "GET");
        System.out.println(connection.getJson());
    }
}

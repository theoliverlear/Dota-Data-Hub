package org.d2database;

import java.util.Optional;

public class PlayerMatch implements GetData {
    String matchId;
    String time;
    String duration;
    String partySize;
    String matchAPIResponse;
    public PlayerMatch(String matchId) {
        this.matchId = matchId;
        this.getData();
    }
    @Override
    public void getData() {
        String apiKey = DotaDataHub.getApiKey();
        String endpoint = "matches/" + this.matchId;
        String method = "GET";
        OpenDotaConnection connection = new OpenDotaConnection(apiKey, endpoint, method);
        connection.connect();
        this.matchAPIResponse = connection.getPageData();
    }
    @Override
    public String retrieveValue(String key) {
        String value = "";
        int startIndex = this.matchAPIResponse.indexOf(key) + key.length() + 3;
        int endIndex = this.matchAPIResponse.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = this.matchAPIResponse.indexOf("}", startIndex);
        }
        value = this.matchAPIResponse.substring(startIndex, endIndex).replace("\"", "");
        return value;
    }
}

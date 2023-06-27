package org.d2database;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player implements GetData {
    String name;
    String id;
    String steamId;
    String rankTier;
    String mmrEstimate;
    WinLoss winLoss;
    String playerAPIResponse;
    String playerMatchAPIResponse;
    ArrayList<String> playerMatchRawData;
    ArrayList<String> playerMatchFormatted;
    ArrayList<PlayerMatch> playerMatches;
    public Player(String id) {
        this.id = id;
        this.getData();
        this.fetchPlayerMatchRawData();
        this.name = this.retrieveValue("personaname");
        this.steamId = this.retrieveValue("steamid");
        this.winLoss = new WinLoss(this.id);
        this.mmrEstimate = this.retrieveValue("estimate");
        this.rankTier = this.retrieveValue("rank_tier");
    }
    public void fetchPlayerMatchRawData() {
        String apiKey = DotaDataHub.getApiKey();
        String endpoint = "players/" + this.id + "/matches";
        String method = "GET";
        OpenDotaConnection connection = new OpenDotaConnection(apiKey, endpoint, method);
        connection.connect();
        this.playerMatchAPIResponse = connection.getPageData();
        Pattern pattern = Pattern.compile("\\{[^\\}]*\\}");
        Matcher matcher = pattern.matcher(this.playerMatchAPIResponse);
        while (matcher.find()) {
            this.playerMatchRawData.add(matcher.group());
        }
    }

    @Override
    public void getData() {
        String apiKey = DotaDataHub.getApiKey();
        String endpoint = "players/" + this.id;
        String method = "GET";
        OpenDotaConnection connection = new OpenDotaConnection(apiKey, endpoint, method);
        connection.connect();
        this.playerAPIResponse = connection.getPageData();
    }
    @Override
    public String retrieveValue(String key) {
        String value = "";
        int startIndex = this.playerAPIResponse.indexOf(key) + key.length() + 3;
        if (key.equals("rank_tier")) {
            startIndex = this.playerAPIResponse.indexOf(key) + key.length() + 2;
        }
        if (key.equals("estimate")) {
            startIndex = this.playerAPIResponse.indexOf(key) + key.length() + 2;
        }
        int endIndex = this.playerAPIResponse.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = this.playerAPIResponse.indexOf("}", startIndex);
        }
        value = this.playerAPIResponse.substring(startIndex, endIndex).replace("\"", "");
        if (value.contains("{estimate:")) {
            int newBeginIndex = value.indexOf("estimate:") + 9;
            value = value.substring(newBeginIndex);
        }
        return value;
    }
    public String getName() {
        return this.name;
    }
    public WinLoss getWinLoss() {
        return this.winLoss;
    }
    public String getSteamId() {
        return this.steamId;
    }
    public String getRankTier() {
        return this.rankTier;
    }
    public String getMmrEstimate() {
        return this.mmrEstimate;
    }
    public String getId() {
        return this.id;
    }
    public String getPlayerAPIResponse() {
        return this.playerAPIResponse;
    }
}

package org.d2database.V1;

import java.util.HashMap;
import java.util.Map;

// This class will take info from a match based on a player. This shorter set
// of data will populate the PlayerMatch object. Since calling the OpenDota API
// for multiple matches in a single item class, when being constructed,
// PlayerMatch will be made in a loop in the calling class which will find
// all the artifacts for a wholistic player dataset. With the dataset, PlayerMatch
// takes the data and creates an object. This object will be added to the
// ArrayList<PlayerMatch> in the Player class.
public class PlayerMatch implements GetData, ParseData {
    Player player;
    String matchId;
    String playerSlot;
    String radiantWin;
    String durationSeconds;
    String gameMode;
    String lobbyType;
    String hero;
    String startTime;
    String version;
    String kills;
    String deaths;
    String assists;
    String skill;
    String avgRank;
    String leaverStatus;
    String partySize;
    String matchData;
    String matchAPIResponse;
    HashMap<String, String> playerMatch;
    public PlayerMatch(String matchData, Player player) {
        this.matchData = matchData;
        this.player = player;
        //this.fetchRawData();
//        this.parseData();
        this.playerMatch = new HashMap<>();

        this.playerSlot = "";
        this.radiantWin = "";
        this.durationSeconds = "";
        this.gameMode = "";
        this.lobbyType = "";
        this.hero = "";
        this.startTime = "";
        this.version = "";
        this.kills = "";
        this.deaths = "";
        this.assists = "";
        this.skill = "";
        this.avgRank = "";
        this.leaverStatus = "";
        this.partySize = "";
        this.assignWithHashMap();
    }
    public void setPlayerMatchData() {
        this.playerMatch.put("match_id", this.retrieveValue("match_id"));
    }
    // this method will work for Match class not this one
    @Override
    public void fetchRawData() {
        String apiKey = DotaDataHub.getApiKey();
        String endpoint = "/players/" + this.player.getId() + "/matches";
        String method = "GET";
        OpenDotaConnection connection = new OpenDotaConnection(apiKey, endpoint, method);
        connection.connect();
        this.matchAPIResponse = connection.getPageData();
        System.out.println("Match API Response: " + this.matchAPIResponse);
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
    @Override
    public void parseData() {
        String tempRawData = this.matchData;
        String[] keys = {"match_id", "player_slot", "radiant_win", "duration",
                         "game_mode", "lobby_type", "hero", "start_time",
                         "version", "kills", "deaths", "assists", "skill",
                         "leaver_status", "party_size"};
        for (String key : keys) {
            int keyValBuffer = 3;
            int keyLength = key.length();
            int startIndex = tempRawData.indexOf(key);
            int keyStarter = startIndex + keyLength + keyValBuffer;
            int endIndex = tempRawData.indexOf(",", startIndex);
            String valueSubString;
            if (tempRawData.charAt(endIndex - 1) == '"') {
                valueSubString = tempRawData.substring(keyStarter,
                                                  endIndex - 2);
            } else {
                valueSubString = tempRawData.substring(keyStarter,
                                                  endIndex - 1);
            }
            System.out.println("Putting " + key + " with " + valueSubString);
            this.playerMatch.put(key, valueSubString);
        }
    }
    public void assignWithHashMap() {
        for (Map.Entry<String, String> entry : this.playerMatch.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("Assigning " + key + " with " + value);
            switch (key) {
                case "match_id" -> this.matchId = value;
                case "player_slot" -> this.playerSlot = value;
                case "radiant_win" -> this.radiantWin = value;
                case "duration" -> this.durationSeconds = value;
                case "game_mode" -> this.gameMode = value;
                case "lobby_type" -> this.lobbyType = value;
                case "hero" -> this.hero = value;
                case "start_time" -> this.startTime = value;
                case "version" -> this.version = value;
                case "kills" -> this.kills = value;
                case "deaths" -> this.deaths = value;
                case "assists" -> this.assists = value;
                case "skill" -> this.skill = value;
                case "leaver_status" -> this.leaverStatus = value;
                case "party_size" -> this.partySize = value;
                default -> throw new IllegalStateException("Unexpected value: "
                                                           + key);
            }
        }
    }
    @Override
    public String toString() {
        return "PlayerMatch {" +
                "matchId='" + matchId + '\'' +
                ", playerSlot='" + playerSlot + '\'' +
                ", radiantWin='" + radiantWin + '\'' +
                ", durationSeconds='" + durationSeconds + '\'' +
                ", gameMode='" + gameMode + '\'' +
                ", lobbyType='" + lobbyType + '\'' +
                ", hero='" + hero + '\'' +
                ", startTime='" + startTime + '\'' +
                ", version='" + version + '\'' +
                ", kills='" + kills + '\'' +
                ", deaths='" + deaths + '\'' +
                ", assists='" + assists + '\'' +
                ", skill='" + skill + '\'' +
                ", avgRank='" + avgRank + '\'' +
                ", leaverStatus='" + leaverStatus + '\'' +
                ", partySize='" + partySize + '\'' +
                '}';
    }
    public String getMatchAPIResponse() {
        return this.matchAPIResponse;
    }
    public HashMap<String, String> getPlayerMatch() {
        return this.playerMatch;
    }
}

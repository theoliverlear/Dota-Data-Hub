package org.d2database.V2.Match;

import org.d2database.V2.ApiConnection;
import org.d2database.V2.JsonData;
import org.d2database.V2.ParseJsonData;
import org.d2database.V2.Player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Match implements JsonData, ParseJsonData {
    //---------------------------Match-Variables------------------------------
    String matchId;
    StringBuilder matchJson;
    HashMap<String, String> matchKeyValue;
    ArrayList<Player> players;
    //-----------------------------Constructor---------------------------------
    public Match(String matchId) {
        this.matchId = matchId;
        this.matchJson = new StringBuilder();
        this.fetchJson();
        this.players = new ArrayList<>();
        this.fetchPlayersFromJson();
    }
    //------------------------------Match-Data--------------------------------

    //------------------------------Fetch-Json--------------------------------
    @Override
    public void fetchJson() {
        String connPath = "matches/" + this.matchId;
        ApiConnection matchIdJson = new ApiConnection(connPath, "GET");
        this.matchJson = matchIdJson.getJson();
    }
    //-----------------------Fetch-Dataset-From-Json--------------------------
    @Override
    public void fetchDatasetFromJson() {
        String[] accountID = {"(account_id)", "(\":)", "(.[^,]*)"};
        String[][] matchRegexes = {accountID};
        for (String[] regexes : matchRegexes) {
            String keyRegex = regexes[0];
            String bufferRegex = regexes[1];
            String valueRegex = regexes[2];
            String regex = keyRegex + bufferRegex + valueRegex;
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(this.matchJson);
        }
    }
    //-----------------------Fetch-Players-From-Json--------------------------
    public void fetchPlayersFromJson() {
        String[] accountID = {"(account_id)", "(\":)", "(.[^,]*)"};
        String[][] matchRegexes = {accountID};
        for (String[] regexes : matchRegexes) {
            String keyRegex = regexes[0];
            String bufferRegex = regexes[1];
            String valueRegex = regexes[2];
            String regex = keyRegex + bufferRegex + valueRegex;
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(this.matchJson);
            HashSet<String> playerIdMatches = new HashSet<>();
            while (matcher.find()) {
                String matchKey = matcher.group(1);
                String matchValue = matcher.group(3);
                if (!matchValue.contains("null")) {
                    playerIdMatches.add(matchValue);
                }
            }
            playerIdMatches.forEach(playerId -> {
                this.players.add(new Player(playerId));
            });
        }
    }
    //-------------------------------Getters----------------------------------
    public String getMatchId() {
        return this.matchId;
    }
    public StringBuilder getMatchJson() {
        return this.matchJson;
    }
    public HashMap<String, String> getMatchKeyValue() {
        return this.matchKeyValue;
    }
    public ArrayList<Player> getPlayers() {
        return this.players;
    }
    //-------------------------------Setters----------------------------------
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
    public void setMatchJson(StringBuilder matchJson) {
        this.matchJson = matchJson;
    }
    public void setMatchKeyValue(HashMap<String, String> matchKeyValue) {
        this.matchKeyValue = matchKeyValue;
    }
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    //--------------------------Other-Functions-------------------------------
    public void printMatchJson() {
        System.out.println(this.matchJson);
    }
}

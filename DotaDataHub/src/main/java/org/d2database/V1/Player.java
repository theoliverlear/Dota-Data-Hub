package org.d2database.V1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;
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
    ArrayList<String> playerMatchIds;
    ArrayList<HashMap<String, String>> playerMatchesData;
    ArrayList<PlayerMatch> playerMatches;
    int threads = Runtime.getRuntime().availableProcessors();
    ExecutorService executor = Executors.newFixedThreadPool(threads / 2);

    public Player(String id) {
        this.playerMatchRawData = new ArrayList<>();
        this.playerMatchIds = new ArrayList<>();
        this.playerMatches = new ArrayList<>();
        this.id = id;
        this.fetchRawData();
        this.fetchPlayerMatchRawData();
        this.fetchMatchId();
        this.generatePlayerMatches();
        //this.fetchPlayerMatch();
        this.name = this.retrieveValue("personaname");
        this.steamId = this.retrieveValue("steamid");
        this.winLoss = new WinLoss(this.id);
        this.mmrEstimate = this.retrieveValue("estimate");
        this.rankTier = this.retrieveValue("rank_tier");
    }
    public void fetchPlayerMatchRawData() {
        String apiKey = DotaDataHub.getApiKey();
        String endpoint = "players/" + this.id + "/matches?limit=1";
        String method = "GET";
        OpenDotaConnection connection = new OpenDotaConnection(apiKey, endpoint, method);
        connection.connect();
        this.playerMatchAPIResponse = connection.getPageData();
        Pattern pattern = Pattern.compile("\\{[^\\}]*\\}");
        // A left curly brace, followed by any number of characters that are
        // not a right curly brace, followed by a right curly brace.
        Matcher matcher = pattern.matcher(this.playerMatchAPIResponse);
        while (matcher.find()) {
            String matchData = matcher.group();
            System.out.println("Match Data: " + matchData);
            this.playerMatchRawData.add(matchData);
        }
    }
    public void generatePlayerMatches() {
        for (String match : this.playerMatchRawData) {
            PlayerMatch playerMatch = new PlayerMatch(match, this);
            this.playerMatches.add(playerMatch);
        }
    }
    @Override
    public void fetchRawData() {
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


    public void fetchMatchId() {
        for (String match : this.playerMatchRawData) {
            int[] matchIdIndexes = {match.indexOf("match_id") + 10,
                    match.indexOf(",", match.indexOf("match_id"))};
            String matchId = match.substring(matchIdIndexes[0], matchIdIndexes[1]);
            this.playerMatchIds.add(matchId);
        }
    }
    public void fetchPlayerMatch() {
        ArrayList<Callable<PlayerMatch>> tasks = new ArrayList<>();
        for (String matchId : this.playerMatchIds) {
            Callable<PlayerMatch> task = () -> {
                PlayerMatch playerMatch = new PlayerMatch(matchId, this);
                System.out.println("Fetching match " + matchId);
                //System.out.println(playerMatch.getMatchAPIResponse());
                //Thread.sleep(65);
                return playerMatch;
            };
            tasks.add(task);

        }
        try {
            ArrayList<Future<PlayerMatch>> futures = (ArrayList<Future<PlayerMatch>>) executor.invokeAll(tasks);
            for (Future<PlayerMatch> future : futures) {
                this.playerMatches.add(future.get());
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        } finally {
            executor.shutdown();
        }

//            PlayerMatch playerMatch = new PlayerMatch(matchId);
//            System.out.println(playerMatch.getMatchAPIResponse());
//            this.playerMatches.add(playerMatch);

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
    public ArrayList<String> getPlayerMatchRawData() {
        return this.playerMatchRawData;
    }
    public ArrayList<String> getPlayerMatchIds() {
        return this.playerMatchIds;
    }
    public ArrayList<PlayerMatch> getPlayerMatches() {
        return this.playerMatches;
    }


}

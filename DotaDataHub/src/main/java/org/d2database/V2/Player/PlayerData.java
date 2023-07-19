package org.d2database.V2.Player;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerData {
    String accountId;
    StringBuilder playerJson;
    StringBuilder playerDataJson;
    int numKeyValues;
    HashMap<String, String> playerDataKeyValue;
    public PlayerData(String accountId, StringBuilder playerJson) {
        this.accountId = accountId;
        this.playerJson = playerJson;
        this.calcNumKeyValues();
        playerDataKeyValue = new HashMap<>();
        this.fetchDatasetFromJson();

    }
    // public void invokeStack()
    //-------------------------Verify-Size-Of-Json----------------------------
    public void calcNumKeyValues() {
        String tempPlayerJson = this.playerJson.toString();
        String[] tempPlayerJsonSplit = tempPlayerJson.split(",");
        this.numKeyValues = tempPlayerJsonSplit.length;
    }
    //-----------------------Fetch-Dataset-From-Json--------------------------
    public void fetchDatasetFromJson() {
        //--------------------Regex-Patterns-For-Data-------------------------
        /*
            - personaName ✔
            - rankTier ✔
            - name ✔
            - dotaPlus ✔
            - soloCompetitiveRank ✔
            - competitiveRank ✔
            - leaderboardRank ✔
            - mmrEstimate ✔
            - steamId ✔
            - profileUrl ✔
            - lastLogin ✔
            - locCountryCode ✔
            - isContributor ✔
            - isSubscriber ✔
         */

        String[] personaName = {"(personaname)", "(\":\")", "(.[^\"]*.*?)"};
        String[] rankTier = {"(rank_tier)", "(\":)", "(.[^,}]*.*?)"};
        String[] name = {".[^a](name)", "(\":)", "(.[^,}]*.*?)"};
        String[] dotaPlus = {"(plus)", "(\":)", "(.[^,}]*.*?)"};
        String[] soloCompetitiveRank = {"(solo_competitive_rank)", "(\":)", "(.[^,}]*.*?)"};
        String[] competitiveRank = {".[^_](competitive_rank)", "(\":)", "(.[^,}]*.*?)"};
        String[] leaderboardRank = {"(leaderboard_rank)", "(\":)", "(.[^,}]*.*?)"};
        String[] estimate = {".[^_](estimate)", "(\":)", "(.[^,}]*.*?)"};
        String[] steamId = {"(steamid)", "(\":\")", "(.[^,}\"]*.*?)"};
        String[] profileUrl = {"(profileurl)", "(\":\")", "(.[^,}\"]*.*?)"};
        String[] lastLogin = {"(last_login)", "(\":\")", "(.[^,}\"]*.*?)"};
        String[] isSubscriber = {"(is_subscriber)", "(\":)", "(.[^,}\"]*.*?)"};
        String[] isContributor = {"(is_contributor)", "(\":)", "(.[^,}\"]*.*?)"};

        String[][] regexes = {personaName, rankTier, name, dotaPlus,
                              soloCompetitiveRank, competitiveRank,
                              leaderboardRank, estimate, steamId, profileUrl,
                              lastLogin, isSubscriber, isContributor};


        for (int i = 0; i < regexes.length; i++) {
            String keyRegex = regexes[i][0];
            String bufferRegex = regexes[i][1];
            String valueRegex = regexes[i][2];
            String regex = keyRegex + bufferRegex + valueRegex;
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(this.playerJson.toString());
            while (matcher.find()) {
                System.out.println(matcher.group(1));
                System.out.println(matcher.group(2));
                System.out.println(matcher.group(3));
            }
        }
    }
}

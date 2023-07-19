package org.d2database.V2.Player;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class PlayerData {
    //-----------------------Variables-Needed-To-Fetch-Data-------------------
    String accountId;
    StringBuilder playerJson;
    int numKeyValues;
    HashMap<String, String> playerDataKeyValue;
    //----------------------------Fetch-Player-Data---------------------------
    String personaName, rankTier, name, dotaPlus, soloCompetitiveRank,
           competitiveRank, leaderboardRank, mmrEstimate, steamId, profileUrl,
           lastLogin, locCountryCode, isContributor, isSubscriber, avatar;
    public PlayerData(String accountId, StringBuilder playerJson) {
        //--------------------------Fetch-Variables---------------------------
        this.accountId = accountId;
        this.playerJson = playerJson;
        this.calcNumKeyValues();
        this.playerDataKeyValue = new HashMap<>();
        this.fetchDatasetFromJson();
        //--------------------------Instantiate-Variables---------------------
        this.instantiateFromDataset();
    }
    // public void invokeStack()
    //-------------------------Verify-Size-Of-Json----------------------------
    public void calcNumKeyValues() {
        String tempPlayerJson = this.playerJson.toString();
        String[] tempPlayerJsonSplit = tempPlayerJson.split(",");
        this.numKeyValues = tempPlayerJsonSplit.length;
    }
    //-----------------------Fetch-Dataset-From-Json--------------------------
    public void fetchDatasetFromJson() { // use ✘ for false and ✔ for true
                                         // or for completed or not completed
        /*
            - personaName         ✔
            - rankTier            ✔
            - name                ✔
            - dotaPlus            ✔
            - soloCompetitiveRank ✔
            - competitiveRank     ✔
            - leaderboardRank     ✔
            - mmrEstimate         ✔
            - steamId             ✔
            - profileUrl          ✔
            - lastLogin           ✔
            - locCountryCode      ✔
            - isContributor       ✔
            - isSubscriber        ✔
            - avatar              ✔
         */
        //----------------------Regex-Patterns-For-Data-----------------------
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
        String[] lastLogin = {"(last_login)", "(\":\")", "(.[^,}\"Z]*.*?)"};
        String[] isSubscriber = {"(is_subscriber)", "(\":)", "(.[^,}\"]*.*?)"};
        String[] isContributor = {"(is_contributor)", "(\":)", "(.[^,}\"]*.*?)"};
        String[] avatar = {"(avatar)", "(\":\")", "(.[^,}\"]*.*?)"};
        //-----------------------Player-Data-Regexes--------------------------
        String[][] fullRegexesList = {personaName, rankTier, name, dotaPlus,
                              soloCompetitiveRank, competitiveRank,
                              leaderboardRank, estimate, steamId, profileUrl,
                              lastLogin, isSubscriber, isContributor, avatar};
        //-------------------------Fetch-Data-To-Map--------------------------
        for (String[] regexes : fullRegexesList) {
            String keyRegex = regexes[0];
            String bufferRegex = regexes[1];
            String valueRegex = regexes[2];
            String regex = keyRegex + bufferRegex + valueRegex;
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(this.playerJson.toString());
            while (matcher.find()) {
                this.playerDataKeyValue.put(matcher.group(1), matcher.group(3));
            }
        }
    }
    //-------------------------Instantiate-From-Map---------------------------
    public void instantiateFromDataset() {
        this.personaName = this.playerDataKeyValue.get("personaname");
        this.rankTier = this.playerDataKeyValue.get("rank_tier");
        this.name = this.playerDataKeyValue.get("name");
        this.dotaPlus = this.playerDataKeyValue.get("plus");
        this.soloCompetitiveRank = this.playerDataKeyValue.get("solo_competitive_rank");
        this.competitiveRank = this.playerDataKeyValue.get("competitive_rank");
        this.leaderboardRank = this.playerDataKeyValue.get("leaderboard_rank");
        this.mmrEstimate = this.playerDataKeyValue.get("estimate");
        this.steamId = this.playerDataKeyValue.get("steamid");
        this.profileUrl = this.playerDataKeyValue.get("profileurl");
        this.lastLogin = this.playerDataKeyValue.get("last_login");
        this.locCountryCode = this.playerDataKeyValue.get("loccountrycode");
        this.isContributor = this.playerDataKeyValue.get("is_contributor");
        this.isSubscriber = this.playerDataKeyValue.get("is_subscriber");
        this.avatar = this.playerDataKeyValue.get("avatar");
    }
    //-------------------------------Getters----------------------------------
    public String getPersonaName() {
        return this.personaName;
    }
    public String getRankTier() {
        return this.rankTier;
    }

    public String getName() {
        return this.name;
    }
    public String getDotaPlus() {
        return this.dotaPlus;
    }

    public String getSoloCompetitiveRank() {
        return this.soloCompetitiveRank;
    }
    public String getCompetitiveRank() {
        return this.competitiveRank;
    }

    public String getLeaderboardRank() {
        return this.leaderboardRank;
    }

    public String getMmrEstimate() {
        return this.mmrEstimate;
    }

    public String getSteamId() {
        return this.steamId;
    }

    public String getProfileUrl() {
        return this.profileUrl;
    }

    public String getLastLogin() {
        return this.lastLogin;
    }

    public String getLocCountryCode() {
        return this.locCountryCode;
    }

    public String getIsContributor() {
        return this.isContributor;
    }

    public String getIsSubscriber() {
        return this.isSubscriber;
    }
    public String getAvatar() {
        return this.avatar;
    }
}

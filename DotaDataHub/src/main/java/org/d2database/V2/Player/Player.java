package org.d2database.V2.Player;

import org.d2database.V2.ApiConnection;
import org.d2database.V2.JsonData;
import org.d2database.V2.ParseJsonData;
import org.d2database.V2.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player implements JsonData, ParseJsonData {
    //---------------------------Player-Variables-----------------------------
    String accountId;
    StringBuilder playerJson;
    int numKeyValues;
    HashMap<String, String> playerKeyValue;
    //---------------------------Matches-Variables----------------------------
    StringBuilder playerMatchesJson;
    ArrayList<SmallPlayerMatch> playerMatches;
    //----------------------------Fetch-Player-Data---------------------------
    String personaName, rankTier, name, dotaPlus, soloCompetitiveRank,
            competitiveRank, leaderboardRank, mmrEstimate, steamId, profileUrl,
            lastLogin, locCountryCode, isContributor, isSubscriber, avatar,
            win, loss, totalMatches, winRate;
    public Player(String accountId) {
        this.accountId = accountId;
        //-----------------------Invoke-Connection----------------------------
        this.fetchJson();  // Initializes playerJson
        this.calcNumKeyValues();
        this.playerKeyValue = new HashMap<>();
        this.fetchDatasetFromJson();
        //-----------------------Instantiate-Variables------------------------
        this.instantiateFromMap();
        this.fetchWinLoss();
        //-------------------Instantiate-Matches-Variables--------------------
        this.playerMatches = new ArrayList<>();
        this.fetchPlayerMatchesJson();
        this.fetchPlayerMatches();

    }
    //-----------------------------Fetch-Json---------------------------------
    @Override
    public void fetchJson() {
        String connPath = "players/" + this.accountId;
        ApiConnection accountIdJson = new ApiConnection(connPath, "GET");
        this.playerJson = accountIdJson.getJson(); // This getJson() is from the API
    }
    // public void invokeStack()
    //-------------------------Verify-Size-Of-Json----------------------------
    public void calcNumKeyValues() {
        String tempPlayerJson = this.playerJson.toString();
        String[] tempPlayerJsonSplit = tempPlayerJson.split(",");
        this.numKeyValues = tempPlayerJsonSplit.length;
    }
    //-----------------------Fetch-Dataset-From-Json--------------------------
    @Override
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
                this.playerKeyValue.put(matcher.group(1), matcher.group(3));
            }
        }
    }
    //-------------------------Instantiate-From-Map---------------------------
    public void instantiateFromMap() {
        this.personaName = this.playerKeyValue.get("personaname");
        this.rankTier = this.playerKeyValue.get("rank_tier");
        this.name = this.playerKeyValue.get("name");
        this.dotaPlus = this.playerKeyValue.get("plus");
        this.soloCompetitiveRank = this.playerKeyValue.get("solo_competitive_rank");
        this.competitiveRank = this.playerKeyValue.get("competitive_rank");
        this.leaderboardRank = this.playerKeyValue.get("leaderboard_rank");
        this.mmrEstimate = this.playerKeyValue.get("estimate");
        this.steamId = this.playerKeyValue.get("steamid");
        this.profileUrl = this.playerKeyValue.get("profileurl");
        this.lastLogin = this.playerKeyValue.get("last_login");
        this.locCountryCode = this.playerKeyValue.get("loccountrycode");
        this.isContributor = this.playerKeyValue.get("is_contributor");
        this.isSubscriber = this.playerKeyValue.get("is_subscriber");
        this.avatar = this.playerKeyValue.get("avatar");
    }
    //------------------------Fetch-Win-Loss-And-Rate-------------------------
    public void fetchWinLoss() {
        PlayerWinLoss playerWinLoss = new PlayerWinLoss(this.accountId);
        this.win = playerWinLoss.getWin();
        this.loss = playerWinLoss.getLose();
        this.totalMatches = playerWinLoss.getTotalMatches();
        this.winRate = playerWinLoss.getWinRate();
    }
    //===========================-Player-Matches-=============================

    //------------------------Player-Matches-Connection-----------------------
    public void fetchPlayerMatchesJson() {
        String path = "players/" + this.accountId + "/matches";
        ApiConnection matchesJson = new ApiConnection(path, "GET");
        this.playerMatchesJson = matchesJson.getJson();
    }
    //-----------------------------Fetch-Matches------------------------------
    public void fetchPlayerMatches() {
        Pattern matchPattern = Pattern.compile("\\{.[^\\}]*");
        Matcher matchMatcher = matchPattern.matcher(this.playerMatchesJson.toString());
        while (matchMatcher.find()) {
            HashMap<String, String> matchKeyValue = new HashMap<>();
            //-------------------------Match-Regex----------------------------
            // (match_id)(":)(.[^",]*)
            String[] matchId = {"(match_id)", "(\":)", "(.[^\",]*)"};
            // (player_slot)(":)(.[^",]*)
            String[] playerSlot = {"(player_slot)", "(\":)", "(.[^\",]*)"}; // ref. player_color.json
            // (radiant_win)(":)(.[^",]*)
            String[] radiantWin = {"(radiant_win)", "(\":)", "(.[^\",]*)"};
            // (duration)(":)(.[^",]*)
            String[] duration = {"(duration)", "(\":)", "(.[^\",]*)"};
            // (game_mode)(":)(.[^",]*)
            String[] gameMode = {"(game_mode)", "(\":)", "(.[^\",]*)"};
            // (lobby_type)(":)(.[^",]*)
            String[] lobbyType = {"(lobby_type)", "(\":)", "(.[^\",]*)"};
            // (hero_id)(":)(.[^",]*)
            String[] heroId = {"(hero_id)", "(\":)", "(.[^\",]*)"};
            // (start_time)(":)(.[^",]*)
            String[] startTime = {"(start_time)", "(\":)", "(.[^\",]*)"};
            // (version)(":)(.[^",]*)
            String[] version = {"(version)", "(\":)", "(.[^\",]*)"};
            // (kills)(":)(.[^",]*)
            String[] kills = {"(kills)", "(\":)", "(.[^\",]*)"};
            // (deaths)(":)(.[^",]*)
            String[] deaths = {"(deaths)", "(\":)", "(.[^\",]*)"};
            // (assists)(":)(.[^",]*)
            String[] assists = {"(assists)", "(\":)", "(.[^\",]*)"};
            // (average_rank)(":)(.[^",]*)
            String[] averageRank = {"(average_rank)", "(\":)", "(.[^\",]*)"};
            // (leaver_status)(":)(.[^",]*)
            String[] leaverStatus = {"(leaver_status)", "(\":)", "(.[^\",]*)"};
            // (party_size)(":)(.[^",]*)
            String[] partySize = {"(party_size)", "(\":)", "(.[^\",]*)"};
            //------------------------Match-Data-Regex------------------------
            String[][] playerMatchRegexes = {matchId, playerSlot, radiantWin,
                    duration, gameMode, lobbyType, heroId, startTime, version,
                    kills, deaths, assists, averageRank, leaverStatus, partySize};
            for (String[] regexes : playerMatchRegexes) {
                String keyRegex = regexes[0];
                String bufferRegex = regexes[1];
                String valueRegex = regexes[2];
                String regex = keyRegex + bufferRegex + valueRegex;
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(matchMatcher.group());
                while (matcher.find()) {
                    matchKeyValue.put(matcher.group(1), matcher.group(3));
                }
            }
            this.instantiateAddMatchesFromMap(matchKeyValue);
        }
    }
    //---------------------Instantiate-Add-Matches-From-Map-------------------
    public void instantiateAddMatchesFromMap(HashMap<String, String> matchKeyValue) {
        String accountIdString = this.accountId;
        String matchIdString = matchKeyValue.get("match_id");
        String playerSlotString = matchKeyValue.get("player_slot");
        String playerTeamString = Utility.determineTeam(matchKeyValue.get("player_slot"));
        String playerColorString = Utility.determineColor(playerSlotString);
        String radiantWinString = matchKeyValue.get("radiant_win");
        String playerWinString = Utility.determineMatchOutcome(radiantWinString, playerTeamString);
        String durationString = matchKeyValue.get("duration");
        String gameModeString = matchKeyValue.get("game_mode");
        String lobbyTypeString = matchKeyValue.get("lobby_type");
        String heroIdString = matchKeyValue.get("hero_id");
        String startTimeString = matchKeyValue.get("start_time");
        String versionString = matchKeyValue.get("version");
        String killsString = matchKeyValue.get("kills");
        String deathsString = matchKeyValue.get("deaths");
        String assistsString = matchKeyValue.get("assists");
        String averageRankString = matchKeyValue.get("average_rank");
        String leaverStatusString = matchKeyValue.get("leaver_status");
        String partySizeString = matchKeyValue.get("party_size").replace("}", "").trim();
        if (partySizeString.contains("null")) {
            partySizeString = "null";
        }

        SmallPlayerMatch smallPlayerMatch = new SmallPlayerMatch(
                accountIdString, matchIdString, playerTeamString,
                playerWinString, playerColorString, playerWinString,
                durationString, gameModeString, lobbyTypeString, heroIdString,
                startTimeString, versionString, killsString, deathsString,
                assistsString, averageRankString, leaverStatusString,
                partySizeString);
        this.playerMatches.add(smallPlayerMatch);
    }
    //-------------------------------Getters----------------------------------
    public StringBuilder getPlayerJson() {
        return this.playerJson;
    }
    public String getAccountId() {
        return this.accountId;
    }
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
    public String getWin() {
        return this.win;
    }
    public String getLoss() {
        return this.loss;
    }
    public String getTotalMatches() {
        return this.totalMatches;
    }
    public String getWinRate() {
        return this.winRate;
    }

    public ArrayList<SmallPlayerMatch> getPlayerMatches() {
        return this.playerMatches;
    }
}

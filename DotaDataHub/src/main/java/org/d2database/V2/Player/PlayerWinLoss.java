package org.d2database.V2.Player;

import org.d2database.V2.ApiConnection;
import org.d2database.V2.JsonData;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerWinLoss implements JsonData {
    //------------------------Response-Data-Variables-------------------------
    String accountId;
    StringBuilder playerJson;
    HashMap<String, String> winLossKeyValue;
    //------------------------Data-From-Json-Variables------------------------
    String win;
    String lose;
    String totalMatches;
    String winRate;
    public PlayerWinLoss(String accountId) {
        this.accountId = accountId;
        this.fetchJson();
        this.winLossKeyValue = new HashMap<>();
        this.fetchDatasetFromJson();
        this.instantiateFromMap();
    }
    //------------------------------Get-Json----------------------------------
    @Override
    public void fetchJson() {
        String connPath = "players/" + this.accountId + "/wl";
        ApiConnection accountIdWLJson = new ApiConnection(connPath, "GET");
        this.playerJson = accountIdWLJson.getJson();
    }
    //=======================-Instantiate-Variables-==========================

    //-----------------------Fetch-Dataset-From-Json--------------------------
    public void fetchDatasetFromJson() { // use ✘ for false and ✔ for true
                                         // or for completed or not completed
        /*
            - win  ✔
            - lose ✔
         */
        //----------------------Regex-Patterns-For-Data-----------------------
        String[] win = {"(win)", "(\":)", "(.[^,}]*.*?)"};
        String[] loss = {"(lose)", "(\":)", "(.[^,}]*.*?)"};
        //------------------------Win-Loss-Data-Regexes-----------------------
        String[][] fullRegexesList = {win, loss};
        //-------------------------Fetch-Data-To-Map--------------------------
        for (String[] regexes : fullRegexesList) {
            String keyRegex = regexes[0];
            String bufferRegex = regexes[1];
            String valueRegex = regexes[2];
            String regex = keyRegex + bufferRegex + valueRegex;
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(this.playerJson.toString());
            while (matcher.find()) {
                this.winLossKeyValue.put(matcher.group(1), matcher.group(3));
            }
        }
    }
    //-------------------------Calculate-Win-Rate-----------------------------
    public void calcWinRate() {
        double tempWinRate = ((double) Integer.parseInt(this.win) /
                             (double) Integer.parseInt(this.totalMatches)) * 100;
        String formattedWinRate = String.format("%.2f", tempWinRate);
        this.winRate = formattedWinRate;
    }
    //-------------------------Instantiate-From-Map---------------------------
    public void instantiateFromMap() {
        this.win = this.winLossKeyValue.get("win");
        this.lose = this.winLossKeyValue.get("lose");
        this.totalMatches = String.valueOf(Integer.parseInt(this.win) +
                            Integer.parseInt(this.lose));
        this.calcWinRate();
    }
    //-----------------------------Getters------------------------------------
    public String getWin() {
        return this.win;
    }
    public String getLose() {
        return this.lose;
    }
    public String getTotalMatches() {
        return this.totalMatches;
    }
    public String getWinRate() {
        return this.winRate;
    }
}

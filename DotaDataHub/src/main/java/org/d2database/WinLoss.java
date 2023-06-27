package org.d2database;

import java.util.ArrayList;

public class WinLoss {
    String id;
    String win;
    String loss;
    String winRate;
    String winLossAPIResponse;
    ArrayList<PlayerMatch> matches;
    public WinLoss(String id) {
        this.id = id;
        this.getWinLossData();
        this.win = this.retrieveValue("win");
        this.loss = this.retrieveValue("lose");
        this.winRate = calculateWinRate();
    }
    public String calculateWinRate() {
        double win = Integer.parseInt(this.win);
        double loss = Integer.parseInt(this.loss);
        double total = win + loss;
        double winRate = (win / total) * 100;
        String winRateFormatted = String.format("%.2f", winRate);
        return winRateFormatted;
    }
    public void getWinLossData() {
        String apiKey = DotaDataHub.getApiKey();
        String endpoint = "players/" + this.id + "/wl";
        String method = "GET";
        OpenDotaConnection connection = new OpenDotaConnection(apiKey, endpoint, method);
        connection.connect();
        this.winLossAPIResponse = connection.getPageData();
    }
    public String retrieveValue(String key) {
        int startIndex = this.winLossAPIResponse.indexOf(key) + key.length() + 2;
        int endIndex = this.winLossAPIResponse.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = this.winLossAPIResponse.indexOf("}");
        }
        String value = this.winLossAPIResponse.substring(startIndex, endIndex);
        return value;
    }
    public String getWin() {
        return this.win;
    }
    public String getLoss() {
        return this.loss;
    }
    public String getWinRate() {
        return this.winRate;
    }

}

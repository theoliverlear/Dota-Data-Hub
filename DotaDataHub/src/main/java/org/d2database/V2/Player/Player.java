package org.d2database.V2.Player;

import org.d2database.V2.ApiConnection;

public class Player {
    String accountId;
    StringBuilder playerJson;
    public Player(String accountId) {
        this.accountId = accountId;

        //-----------------------Invoke-Connection----------------------------
        this.getJson();  // Initializes playerJson
        //-------------------Create-Player-Data-Object------------------------
        PlayerData playerData = new PlayerData(this.accountId, this.playerJson);

    }
    public void getJson() {
        String connPath = "players/" + this.accountId;
        ApiConnection accountIdJson = new ApiConnection(connPath, "GET");
        this.playerJson = accountIdJson.getJson();
    }
    //-------------------------------Getters----------------------------------
    public StringBuilder getPlayerJson() {
        return this.playerJson;
    }
}

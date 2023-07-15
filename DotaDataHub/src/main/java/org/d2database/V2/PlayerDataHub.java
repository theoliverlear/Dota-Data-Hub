package org.d2database.V2;

import org.d2database.V2.Player.Player;

public class PlayerDataHub {
    String accountId;
    Player player;
    public PlayerDataHub(String accountId) {
        this.accountId = accountId;
        this.player = new Player(this.accountId);
    }
    public static void main(String[] args) {
        //----------------------Connection-Variables--------------------------
        String path = "C:\\Users\\olive\\OneDrive\\Documents\\" +
                      "Key Folder\\OpenDota\\OpenDotaAPIKeys.txt";
        FileDataRetriever fileDataRetriever = new FileDataRetriever(0, path);
        String apiKey = fileDataRetriever.getData();
        Data.API_KEY.setData(apiKey);
        String methodCall = "GET";
        String baseURL = Data.BASE_URL.getData();
        PlayerDataHub playerHub = new PlayerDataHub("348245720");
        //-------------------------Print-Statements---------------------------
        System.out.println(playerHub.getPlayer().getPlayerJson());
    }
    //-------------------------------Getters----------------------------------
    public String getAccountId() {
        return this.accountId;
    }
    public Player getPlayer() {
        return this.player;
    }
}

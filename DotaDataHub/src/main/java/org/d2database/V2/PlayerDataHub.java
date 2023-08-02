package org.d2database.V2;

import org.d2database.V2.Player.Player;
import org.d2database.V2.Player.PlayerDatabase;

public class PlayerDataHub {
    String accountId;
    Player player;
    public PlayerDataHub(String accountId) {
        this.accountId = accountId;
        this.player = new Player(this.accountId);
    }
    public static void main(String[] args) {

        //-------------------------Set-API-Variable---------------------------
        String path = "C:\\Users\\olive\\OneDrive\\Documents\\" +
                      "Key Folder\\OpenDota\\OpenDotaAPIKeys.txt";
        FileDataRetriever fileDataRetriever = new FileDataRetriever(0, path);
        String apiKey = fileDataRetriever.getData();
        Data.API_KEY.setData(apiKey);
        //-----------------------Connection-Variables-------------------------
        PlayerDataHub playerHub = new PlayerDataHub("348245720");
        PlayerDataHub playerHub2 = new PlayerDataHub("192960022");
        PlayerDataHub playerHub3 = new PlayerDataHub("126867101");
        //-------------------------Print-Statements---------------------------
        PlayerDatabase playerDB = new PlayerDatabase();
        System.out.println("Contains player: " + playerDB.containsPlayer(playerHub.getPlayer()));
        playerDB.insertSequence(playerHub.getPlayer());

        System.out.println("Contains player: " + playerDB.containsPlayer(playerHub2.getPlayer()));
        playerDB.insertSequence(playerHub2.getPlayer());

        System.out.println("Contains player: " + playerDB.containsPlayer(playerHub3.getPlayer()));
        playerDB.insertSequence(playerHub3.getPlayer());

        // Like how the matches for each player is a web crawler for the matches,
        // the players each are a web crawler for other players.
    }
    //-------------------------------Getters----------------------------------
    public String getAccountId() {
        return this.accountId;
    }
    public Player getPlayer() {
        return this.player;
    }
}

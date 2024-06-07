package org.d2database.V2.executable;

import org.d2database.V2.Hub.PlayerHub;
import org.d2database.V2.Player.PlayerDatabase;
/*
123216701
126867101
192960022
262604952
348245720

 */
public class AddPlayer {
    public static void main(String[] args) {
        PlayerHub playerHub = new PlayerHub("123216701");
        PlayerHub playerHubTwo = new PlayerHub("126867101");
        PlayerHub playerHubThree = new PlayerHub("192960022");
        PlayerHub playerHubFour = new PlayerHub("262604952");
        PlayerHub playerHubFive = new PlayerHub("348245720");
        PlayerHub[] playerHubs = {
                playerHub,
                playerHubTwo,
                playerHubThree,
                playerHubFour,
                playerHubFive
        };
        PlayerDatabase playerDatabase = new PlayerDatabase();
        for (PlayerHub hub : playerHubs) {
            playerDatabase.insertSequence(hub.getPlayer());
        }
    }
}

package org.d2database.V2.Hub;

import org.d2database.V2.Data;
import org.d2database.V2.FileLineRetriever;
import org.d2database.V2.Player.Player;
import org.d2database.V2.Player.PlayerDatabase;
import org.d2database.V2.Player.SmallPlayerMatch;
import org.d2database.V2.Player.SmallPlayerMatchDatabase;

import java.util.ArrayList;

public class PlayerHub {
    String accountId;
    Player player;
    public PlayerHub(String accountId) {
        this.accountId = accountId;
        this.player = new Player(this.accountId);
        System.out.println("Starting a Player Hub for player: " + this.accountId);
    }
    public static void main(String[] args) {
        //-------------------------Set-API-Variable---------------------------
        String path = "src/main/resources/api_key.txt";
        FileLineRetriever fileLineRetriever = new FileLineRetriever(0, path);
        String apiKey = fileLineRetriever.getData();
        Data.API_KEY.setData(apiKey);
        //-----------------------Connection-Variables-------------------------
        PlayerHub playerHubOne = new PlayerHub("348245720");
        PlayerHub playerHubTwo = new PlayerHub("192960022");
        PlayerHub playerHubThree = new PlayerHub("126867101");
        //-------------------------Print-Statements---------------------------
        // Like how the matches for each player is a web crawler for the matches,
        // the players each are a web crawler for other players.

        SmallPlayerMatchDatabase smallPlayerMatchDatabase = new SmallPlayerMatchDatabase();

        Thread threadOne = new Thread(() -> {
            for (SmallPlayerMatch match : playerHubOne.getPlayer().getPlayerMatches()) {
                smallPlayerMatchDatabase.insertSequence(match);
            }
        });
        Thread threadTwo = new Thread(() -> {
            for (SmallPlayerMatch match : playerHubTwo.getPlayer().getPlayerMatches()) {
                smallPlayerMatchDatabase.insertSequence(match);
            }
        });
        Thread threadThree = new Thread(() -> {
            for (SmallPlayerMatch match : playerHubThree.getPlayer().getPlayerMatches()) {
                smallPlayerMatchDatabase.insertSequence(match);
            }
        });
//        threadOne.start();
//        threadTwo.start();
//        threadThree.start();
//        try {
//            threadOne.join();
//            threadTwo.join();
//            threadThree.join();
//        } catch (InterruptedException ex) {
//            throw new RuntimeException(ex);
//        }
        PlayerDatabase playerDatabase = new PlayerDatabase();
        MatchHub matchHubOne = new MatchHub("1772444224");
        ArrayList<Player> players = matchHubOne.getMatch().getPlayers();
        ArrayList<Thread> threads = new ArrayList<>();
        players.forEach(player -> {
            Thread thread = new Thread(() -> {
                PlayerHub playerHub = new PlayerHub(player.getAccountId());
                playerDatabase.insertSequence(playerHub.getPlayer());
//                for (SmallPlayerMatch match : playerHub.getPlayer().getPlayerMatches()) {
//                    smallPlayerMatchDatabase.insertSequence(match);
//                }
            });
            threads.add(thread);
        });
        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    //-------------------------------Getters----------------------------------
    public String getAccountId() {
        return this.accountId;
    }
    public Player getPlayer() {
        return this.player;
    }
}

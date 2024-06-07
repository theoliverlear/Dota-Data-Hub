package org.d2database.V2.executable;

import org.d2database.V2.ApiConnection;
import org.d2database.V2.Hub.MatchHub;

public class MatchResponse {
    public static void main(String[] args) {
        String match = "1772444224";
        MatchHub matchHub = new MatchHub(match);
        matchHub.getMatch().getPlayers().forEach(player -> {
            System.out.println(player.getAccountId());
        });
    }
}

package org.d2database.V2.Hub;

import org.d2database.V2.Match.Match;

public class MatchHub {
    String matchId;
    Match match;
    public MatchHub(String matchId) {
        this.matchId = matchId;
        this.match = new Match(this.matchId);
    }
    public static void main(String[] args) {

    }
    //-------------------------------Getters----------------------------------
    public String getMatchId() {
        return this.matchId;
    }
    public Match getMatch() {
        return this.match;
    }
    //-------------------------------Setters----------------------------------
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
    public void setMatch(Match match) {
        this.match = match;
    }
}

package org.d2database.V2.Player;

public class SmallPlayerMatch {
    String matchId, playerTeam, playerColor, playerWin, radiantWin, duration,
            gameMode, lobbyType, heroId, startTime, version, kills, deaths,
            assists, averageRank, leaverStatus, partySize;
    public SmallPlayerMatch(String matchId, String playerTeam, String playerWin, String playerColor,
                            String radiantWin, String duration, String gameMode,
                            String lobbyType, String heroId, String startTime,
                            String version, String kills, String deaths, String assists,
                            String averageRank, String leaverStatus,
                            String partySize) {
        this.matchId = matchId;
        this.playerTeam = playerTeam;
        this.playerWin = playerWin;
        this.playerColor = playerColor;
        this.radiantWin = radiantWin;
        this.duration = duration;
        this.gameMode = gameMode;
        this.lobbyType = lobbyType;
        this.heroId = heroId;
        this.startTime = startTime;
        this.version = version;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.averageRank = averageRank;
        this.leaverStatus = leaverStatus;
        this.partySize = partySize;
    }

}

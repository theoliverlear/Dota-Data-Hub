package org.d2database.V2.Player;

import org.d2database.V2.Utility;

public class SmallPlayerMatch {
    String accountId, matchId, playerTeam, playerColor, playerWin, radiantWin,
            duration, gameMode, hero, startTime, version, kills,
            deaths, assists, averageRank, leaverStatus, partySize;
    String lobbyType;
    String kda;
    public SmallPlayerMatch(String accountId, String matchId,
                            String playerTeam, String playerWin,
                            String playerColor, String radiantWin,
                            String duration, String gameModeId,
                            String lobbyTypeId, String heroId, String startTimeEpoch,
                            String versionId, String kills, String deaths,
                            String assists, String averageRank,
                            String leaverStatus, String partySize) {
        this.accountId = accountId;
        this.matchId = matchId;
        this.playerTeam = playerTeam;
        this.playerWin = playerWin;
        this.playerColor = playerColor;
        this.radiantWin = radiantWin;
        this.duration = duration;
        this.gameMode = Utility.determineGameMode(gameModeId);
        this.lobbyType = Utility.determineLobbyType(lobbyTypeId);
        this.hero = Utility.determineHero(heroId);
        this.startTime = Utility.determineStartTime(startTimeEpoch);
        this.version = Utility.determineVersion(versionId);
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.kda = "";
        this.fetchKda();
        this.averageRank = Utility.determineRank(averageRank);
        this.leaverStatus = leaverStatus;
        this.partySize = partySize;
    }
    public void fetchKda() {
        double kills = Double.parseDouble(this.kills);
        double assists = Double.parseDouble(this.assists);
        double deaths = Double.parseDouble(this.deaths);
        if (deaths == 0.0) { // Prevent divide by zero/infinity errors;
            deaths = 1.0;
        }
        double kdaNum = (kills + assists) / deaths;
        this.kda = String.format("%.2f", kdaNum);
    }

    public String getAccountId() {
        return this.accountId;
    }
    public String getMatchId() {
        return this.matchId;
    }
    public String getPlayerTeam() {
        return this.playerTeam;
    }
    public String getPlayerWin() {
        return this.playerWin;
    }
    public String getPlayerColor() {
        return this.playerColor;
    }
    public String getRadiantWin() {
        return this.radiantWin;
    }
    public String getDuration() {
        return this.duration;
    }
    public String getGameMode() {
        return this.gameMode;
    }
    public String getLobbyType() {
        return this.lobbyType;
    }
    public String getHero() {
        return this.hero;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public String getVersion() {
        return this.version;
    }
    public String getKills() {
        return this.kills;
    }
    public String getDeaths() {
        return this.deaths;
    }
    public String getAssists() {
        return this.assists;
    }
    public String getKda() {
        return this.kda;
    }
    public String getAverageRank() {
        return this.averageRank;
    }
    public String getLeaverStatus() {
        return this.leaverStatus;
    }
    public String getPartySize() {
        return this.partySize;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }
    public void setPlayerTeam(String playerTeam) {
        this.playerTeam = playerTeam;
    }
    public void setPlayerWin(String playerWin) {
        this.playerWin = playerWin;
    }
    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }
    public void setRadiantWin(String radiantWin) {
        this.radiantWin = radiantWin;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }
    public void setLobbyType(String lobbyType) {
        this.lobbyType = lobbyType;
    }
    public void setHero(String hero) {
        this.hero = hero;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public void setKills(String kills) {
        this.kills = kills;
    }
    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }
    public void setAssists(String assists) {
        this.assists = assists;
    }
    public void setKda(String kda) {
        this.kda = kda;
    }
    public void setAverageRank(String averageRank) {
        this.averageRank = averageRank;
    }
    public void setLeaverStatus(String leaverStatus) {
        this.leaverStatus = leaverStatus;
    }
    public void setPartySize(String partySize) {
        this.partySize = partySize;
    }
}

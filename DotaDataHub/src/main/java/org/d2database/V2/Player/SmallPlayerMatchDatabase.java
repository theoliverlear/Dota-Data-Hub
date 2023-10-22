package org.d2database.V2.Player;

import org.d2database.V2.CoreDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SmallPlayerMatchDatabase extends CoreDatabase {
    public SmallPlayerMatchDatabase() {
        super();
    }
    // TODO: move method to core database
    public Boolean tableExists(String tableName) {
        String query = String.format("SELECT * FROM DotaDataHubDatabase." +
                        "INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = '%s'"
                , tableName);
        return this.queryHasResult(query);
    }
        /*
        match_id BIGINT PRIMARY KEY,
    player_team VARCHAR(7) DEFAULT NULL,
    player_win VARCHAR(5) DEFAULT NULL,
    radiant_win VARCHAR(5) DEFAULT NULL,
    game_mode VARCHAR(255) DEFAULT NULL,
    hero VARCHAR(255) DEFAULT NULL,
    start_time DATETIME DEFAULT NULL,
    duration INT DEFAULT NULL,
    kills INT DEFAULT NULL,
    deaths INT DEFAULT NULL,
    assists INT DEFAULT NULL,
    kda DECIMAL(5,2) DEFAULT NULL,
    lobby_type VARCHAR(255) DEFAULT NULL,
    party_size INT DEFAULT NULL,
    average_rank VARCHAR(255) DEFAULT NULL,
    player_color VARCHAR(255) DEFAULT NULL,
    leaver_status VARCHAR(255) DEFAULT NULL,
    version VARCHAR(255) DEFAULT NULL,
    last_updated DATETIME NOT NULL DEFAULT SWITCHOFFSET(GETDATE(), '-05:00')
     */

    // TODO: move method to core database
    public void createTable(SmallPlayerMatch smallPlayerMatch) {
        String tableName = this.fetchTableName(smallPlayerMatch.getAccountId());
        String query = String.format("CREATE TABLE \"%s\" (" +
                "match_id BIGINT PRIMARY KEY," +
                " player_team VARCHAR(7) DEFAULT NULL," +
                " player_win VARCHAR(5) DEFAULT NULL," +
                " radiant_win VARCHAR(5) DEFAULT NULL," +
                " game_mode VARCHAR(255) DEFAULT NULL," +
                " hero VARCHAR(255) DEFAULT NULL," +
                " start_time DATETIME DEFAULT NULL," +
                " duration INT DEFAULT NULL," +
                " kills INT DEFAULT NULL," +
                " deaths INT DEFAULT NULL," +
                " assists INT DEFAULT NULL," +
                " kda DECIMAL(5,2) DEFAULT NULL," +
                " lobby_type VARCHAR(255) DEFAULT NULL," +
                " party_size INT DEFAULT NULL," +
                " average_rank VARCHAR(255) DEFAULT NULL," +
                " player_color VARCHAR(255) DEFAULT NULL," +
                " leaver_status VARCHAR(255) DEFAULT NULL," +
                " version VARCHAR(255) DEFAULT NULL," +
                " last_updated DATETIME NOT NULL DEFAULT SWITCHOFFSET(GETDATE(), '-05:00'))",
                tableName);
        this.commandQuery(query);
    }
    public String fetchTableName(String accountId) {
        return String.format("SmallMatch_%s", accountId);
    }

    public void insertSequence(SmallPlayerMatch smallPlayerMatch) {
        String tableName = this.fetchTableName(smallPlayerMatch.getAccountId());
        if (!this.tableExists(tableName)) {
            this.createTable(smallPlayerMatch);
        }
        if (!this.tableContainsMatch(tableName, smallPlayerMatch.getMatchId())) {
            String partySize = smallPlayerMatch.getPartySize();
            String query;
            if (partySize.contains("null")) {
                query = String.format("INSERT INTO %s (match_id, player_team, " +
                                "player_win, radiant_win, game_mode, hero, start_time, " +
                                "duration, kills, deaths, assists, kda, lobby_type, " +
                                "party_size, average_rank, player_color, leaver_status, " +
                                "version) VALUES (%s, '%s', '%s', '%s', '%s', '%s', '%s', " +
                                "%s, %s, %s, %s, %s, '%s', NULL, '%s', '%s', '%s', '%s')",
                        tableName, smallPlayerMatch.getMatchId(), smallPlayerMatch.getPlayerTeam(),
                        smallPlayerMatch.getPlayerWin(), smallPlayerMatch.getRadiantWin(),
                        smallPlayerMatch.getGameMode(), smallPlayerMatch.getHero(),
                        smallPlayerMatch.getStartTime(), smallPlayerMatch.getDuration(),
                        smallPlayerMatch.getKills(), smallPlayerMatch.getDeaths(),
                        smallPlayerMatch.getAssists(), smallPlayerMatch.getKda(),
                        smallPlayerMatch.getLobbyType(), smallPlayerMatch.getAverageRank(),
                        smallPlayerMatch.getPlayerColor(), smallPlayerMatch.getLeaverStatus(),
                        smallPlayerMatch.getVersion());
            } else {
                query = String.format("INSERT INTO %s (match_id, player_team, " +
                                "player_win, radiant_win, game_mode, hero, start_time, " +
                                "duration, kills, deaths, assists, kda, lobby_type, " +
                                "party_size, average_rank, player_color, leaver_status, " +
                                "version) VALUES (%s, '%s', '%s', '%s', '%s', '%s', '%s', " +
                                "%s, %s, %s, %s, %s, '%s', %s, '%s', '%s', '%s', '%s')",
                        tableName, smallPlayerMatch.getMatchId(), smallPlayerMatch.getPlayerTeam(),
                        smallPlayerMatch.getPlayerWin(), smallPlayerMatch.getRadiantWin(),
                        smallPlayerMatch.getGameMode(), smallPlayerMatch.getHero(),
                        smallPlayerMatch.getStartTime(), smallPlayerMatch.getDuration(),
                        smallPlayerMatch.getKills(), smallPlayerMatch.getDeaths(),
                        smallPlayerMatch.getAssists(), smallPlayerMatch.getKda(),
                        smallPlayerMatch.getLobbyType(), smallPlayerMatch.getPartySize(),
                        smallPlayerMatch.getAverageRank(), smallPlayerMatch.getPlayerColor(),
                        smallPlayerMatch.getLeaverStatus(), smallPlayerMatch.getVersion());
            }
            System.out.printf("Table: %s - Adding match: %s\n", tableName,
                        smallPlayerMatch.getMatchId());
            this.commandQuery(query);
        }
    }
    public boolean tableContainsMatch(String tableName, String matchId) {
        String query = String.format("SELECT * FROM %s WHERE match_id = %s",
                tableName, matchId);
        return this.queryHasResult(query);
    }
}

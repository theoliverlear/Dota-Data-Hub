package org.d2database.V2.Player;

import org.d2database.V2.CoreDatabase;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class PlayerDatabase extends CoreDatabase {
    public PlayerDatabase() {
        super();
    }

    /*
        --PlayerV1 Columns with Metadata Sorted Relevantly
        -- account_id (INT, unique, NOT NULL, primary key)
        -- persona_name (VARCHAR(255), DEFAULT NULL)
        -- name (VARCHAR(255) DEFAULT NULL)
        -- steam_id (BIGINT DEFAULT NULL)
        -- profile_url (VARCHAR(255) DEFAULT NULL)
        -- avatar (VARCHAR(255) DEFAULT NULL)
        -- loc_country_code (VARCHAR(255) DEFAULT NULL)
        -- last_login (DATETIME DEFAULT NULL)
        -- dota_plus (VARCHAR(4,5) DEFAULT NULL)
        -- is_subscriber (VARCHAR(4,5) DEFAULT NULL)
        -- is_contributor (VARCHAR(4,5) DEFAULT NULL)
        -- mmr_estimate (INT DEFAULT NULL)
        -- rank_tier (INT DEFAULT NULL)
        -- solo_competitive_rank (INT DEFAULT NULL)
        -- competitive_rank (INT DEFAULT NULL)
        -- leaderboard_rank (INT DEFAULT NULL)
        -- win (INT DEFAULT NULL)
        -- loss (INT DEFAULT NULL)
        -- total_matches (INT DEFAULT NULL)
        -- win_rate (DECIMAL(5,2) DEFAULT NULL)
        -- last_updated (DATETIME NOT NULL DEFAULT SWITCHOFFSET(GETDATE(), '-05:00'))
    */
    //-----------------------------Contains-Player----------------------------
    public boolean containsPlayer(Player player) {
        String accountId = player.getAccountId();
        String query = "SELECT * FROM PlayerV1 WHERE account_id = " + accountId;
        boolean containsPlayer = true; // Would rather prevent duplicate entries
                                       // than having to delete them later
        try {
            ResultSet resultSet;
            try (Statement statement = this.getConnection().createStatement()) {
                resultSet = statement.executeQuery(query);
                containsPlayer = resultSet.next();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Failure in querying player in database! " +
                               "Returning true to prevent duplicate entries.");
        }
        return containsPlayer;
    }
    //----------------------------Update-Sequence-----------------------------
    public void insertSequence(Player player) {
        boolean containsPlayer = this.containsPlayer(player);
        if (containsPlayer) {
            boolean playerDataMatches = this.playerDataMatches(player);
            if (!playerDataMatches) {
                this.updatePlayer(player);
            }
        } else {
            this.insertPlayer(player);
        }
    }
    //-----------------------------Insert-Player------------------------------
    public void insertPlayer(Player player) {
        String accountId = player.getAccountId();
        if (!this.containsPlayer(player)) {
            String personaName = player.getPersonaName();
            String name = player.getName();
            String steamId = player.getSteamId();
            String profileUrl = player.getProfileUrl();
            String avatar = player.getAvatar();
            String locCountryCode = player.getLocCountryCode();
            String lastLogin = player.getLastLogin();
            if (lastLogin == null) {
                lastLogin = "NULL";
            }
            String dotaPlus = player.getDotaPlus();
            String isSubscriber = player.getIsSubscriber();
            String isContributor = player.getIsContributor();
            String mmrEstimate = player.getMmrEstimate();
            String rankTier = player.getRankTier();
            String soloCompetitiveRank = player.getSoloCompetitiveRank();
            String competitiveRank = player.getCompetitiveRank();
            String leaderboardRank = player.getLeaderboardRank();
            String win = player.getWin();
            String loss = player.getLoss();
            String totalMatches = player.getTotalMatches();
            String winRate = player.getWinRate();
            String query;
            if (lastLogin.equals("NULL")) {
                query = String.format("INSERT INTO PlayerV1 (account_id," +
                                " persona_name, name, steam_id, profile_url," +
                                " avatar, loc_country_code, last_login, " +
                                "dota_plus, is_subscriber, is_contributor, " +
                                "mmr_estimate, rank_tier, solo_competitive_rank," +
                                " competitive_rank, leaderboard_rank, win, loss," +
                                " total_matches, win_rate) VALUES (%s, '%s', " +
                                "'%s', %s, '%s', '%s', '%s', %s, '%s', '%s', " +
                                "'%s', %s, '%s', %s, %s, %s, %s, %s, %s, %s)",
                        accountId, personaName, name, steamId, profileUrl, avatar,
                        locCountryCode, lastLogin, dotaPlus, isSubscriber,
                        isContributor, mmrEstimate, rankTier, soloCompetitiveRank,
                        competitiveRank, leaderboardRank, win, loss, totalMatches,
                        winRate);
            } else {
                query = String.format("INSERT INTO PlayerV1 (account_id," +
                                " persona_name, name, steam_id, profile_url," +
                                " avatar, loc_country_code, last_login, " +
                                "dota_plus, is_subscriber, is_contributor, " +
                                "mmr_estimate, rank_tier, solo_competitive_rank," +
                                " competitive_rank, leaderboard_rank, win, loss," +
                                " total_matches, win_rate) VALUES (%s, '%s', " +
                                "'%s', %s, '%s', '%s', '%s', '%s', '%s', '%s', " +
                                "'%s', %s, '%s', %s, %s, %s, %s, %s, %s, %s)",
                        accountId, personaName, name, steamId, profileUrl, avatar,
                        locCountryCode, lastLogin, dotaPlus, isSubscriber,
                        isContributor, mmrEstimate, rankTier, soloCompetitiveRank,
                        competitiveRank, leaderboardRank, win, loss, totalMatches,
                        winRate);
            }
            this.commandQuery(query);
        }
    }
    //-----------------------------Match-Player-Data--------------------------
    public boolean playerDataMatches(Player player) {
        boolean playerDataMatches;
        String accountId = player.getAccountId();
        if (this.containsPlayer(player)) {
            String personaName = player.getPersonaName();
            String name = player.getName();
            String steamId = player.getSteamId();
            String profileUrl = player.getProfileUrl();
            String avatar = player.getAvatar();
            String locCountryCode = player.getLocCountryCode();
            String lastLogin = player.getLastLogin();
            String dotaPlus = player.getDotaPlus();
            String isSubscriber = player.getIsSubscriber();
            String isContributor = player.getIsContributor();
            String mmrEstimate = player.getMmrEstimate();
            String rankTier = player.getRankTier();
            String soloCompetitiveRank = player.getSoloCompetitiveRank();
            String competitiveRank = player.getCompetitiveRank();
            String leaderboardRank = player.getLeaderboardRank();
            String win = player.getWin();
            String loss = player.getLoss();
            String totalMatches = player.getTotalMatches();
            String winRate = player.getWinRate();

            String query = String.format("SELECT * FROM PlayerV1 WHERE " +
                            "account_id = %s AND persona_name = '%s' AND " +
                            "name = '%s' AND steam_id = %s AND profile_url = '%s' " +
                            "AND avatar = '%s' AND loc_country_code = '%s' AND " +
                            "last_login = '%s' AND dota_plus = '%s' AND " +
                            "is_subscriber = '%s' AND is_contributor = '%s' AND " +
                            "mmr_estimate = %s AND rank_tier = %s AND " +
                            "solo_competitive_rank = %s AND competitive_rank = %s " +
                            "AND leaderboard_rank = %s AND win = %s AND loss = %s " +
                            "AND total_matches = %s AND win_rate = %s",
                    accountId, personaName, name, steamId, profileUrl, avatar,
                    locCountryCode, lastLogin, dotaPlus, isSubscriber,
                    isContributor, mmrEstimate, rankTier, soloCompetitiveRank,
                    competitiveRank, leaderboardRank, win, loss, totalMatches,
                    winRate);
            this.commandQuery(query);
            try {
                ResultSet resultSet;
                try (Statement statement = this.getConnection().createStatement()) {
                    resultSet = statement.executeQuery(query);
                    playerDataMatches = resultSet.next();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Failure in querying player in database! Returning true" +
                        " to prevent duplicate entries");
                playerDataMatches = true;
            }
        } else {
            System.out.println("Player (" + accountId + ") does " +
                               "not exist in database. Not matching...");
            playerDataMatches = true;
        }
        return playerDataMatches;
    }
    //-----------------------------Update-Player------------------------------
    public void updatePlayer(Player player) {
        String accountId = player.getAccountId();
        if (this.containsPlayer(player)) {
            String personaName = player.getPersonaName();
            String name = player.getName();
            String steamId = player.getSteamId();
            String profileUrl = player.getProfileUrl();
            String avatar = player.getAvatar();
            String locCountryCode = player.getLocCountryCode();
            String lastLogin = player.getLastLogin();
            String dotaPlus = player.getDotaPlus();
            String isSubscriber = player.getIsSubscriber();
            String isContributor = player.getIsContributor();
            String mmrEstimate = player.getMmrEstimate();
            String rankTier = player.getRankTier();
            String soloCompetitiveRank = player.getSoloCompetitiveRank();
            String competitiveRank = player.getCompetitiveRank();
            String leaderboardRank = player.getLeaderboardRank();
            String win = player.getWin();
            String loss = player.getLoss();
            String totalMatches = player.getTotalMatches();
            String winRate = player.getWinRate();

            String query = String.format("UPDATE PlayerV1 SET persona_name = '%s', " +
                            "name = '%s', steam_id = %s, profile_url = '%s', " +
                            "avatar = '%s', loc_country_code = '%s', last_login = '%s', " +
                            "dota_plus = '%s', is_subscriber = '%s', is_contributor = '%s', " +
                            "mmr_estimate = %s, rank_tier = %s, solo_competitive_rank = %s, " +
                            "competitive_rank = %s, leaderboard_rank = %s, win = %s, " +
                            "loss = %s, total_matches = %s, win_rate = %s WHERE " +
                            "account_id = %s",
                    personaName, name, steamId, profileUrl, avatar,
                    locCountryCode, lastLogin, dotaPlus, isSubscriber,
                    isContributor, mmrEstimate, rankTier, soloCompetitiveRank,
                    competitiveRank, leaderboardRank, win, loss, totalMatches,
                    winRate, accountId);
            this.commandQuery(query);
        } else {
            System.out.println("Player (" + accountId + ") does not exist in " +
                               "database. Not updating...");
        }
    }
}

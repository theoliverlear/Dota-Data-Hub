package org.d2database.V2;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utility {
    //-----------------------------Epoch-To-Date------------------------------
    public static String epochToDate(String epoch) {
        long epochLong = Long.parseLong(epoch);
        Instant instant = Instant.ofEpochSecond(epochLong);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String date = dateTimeFormatter.format(localDateTime);
        return date;
    }
    //-----------------------------determineTeam------------------------------
    public static String determineTeam(String playerSlot) {
        String team = switch (playerSlot) {
            case "0", "1", "2", "3", "4"           -> "Radiant";
            case "128", "129", "130", "131", "132" -> "Dire";
            default -> throw new IllegalStateException("Unexpected value: " + playerSlot);
        };
        return team;
    }
    //-----------------------Determine-Match-Outcome--------------------------
    public static String determineMatchOutcome(String radiantWin, String playerTeam) {
        String outcome = switch (radiantWin) {
            case "true" -> {
                if (playerTeam.equals("Radiant")) {
                    yield "win";
                } else {
                    yield "lose";
                }
            }
            case "false" -> {
                if (playerTeam.equals("Radiant")) {
                    yield "lose";
                } else {
                    yield "win";
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + radiantWin);
        };
        return outcome;
    }
    //----------------------------Determine-Color-----------------------------
    public static String determineColor(String playerSlot) {
        /*
          "0": "#3375FF",
          "1": "#66FFBF",
          "2": "#BF00BF",
          "3": "#F3F00B",
          "4": "#FF6B00",
          "128": "#FE86C2",
          "129": "#A1B447",
          "130": "#65D9F7",
          "131": "#008321",
          "132": "#A46900"
         */
        String color = switch (playerSlot) {
            case "0" -> "#3375FF";
            case "1" -> "#66FFBF";
            case "2" -> "#BF00BF";
            case "3" -> "#F3F00B";
            case "4" -> "#FF6B00";
            case "128" -> "#FE86C2";
            case "129" -> "#A1B447";
            case "130" -> "#65D9F7";
            case "131" -> "#008321";
            case "132" -> "#A46900";
            default -> throw new IllegalStateException("Unexpected value: " + playerSlot);
        };
        return color;
    }
    //---------------------------Determine-Leaver-----------------------------
    // TODO: add regex hero parsing
    public static String determineHero(String heroId) {
        int heroIdParsed = Integer.parseInt(heroId);
        String heroName = switch (heroIdParsed) {
            case 0 -> "Unknown";
            case 1 -> "Anti-Mage";
            case 2 -> "Axe";
            case 3 -> "Bane";
            case 4 -> "Bloodseeker";
            case 5 -> "Crystal Maiden";
            case 6 -> "Drow Ranger";
            case 7 -> "Earthshaker";
            case 8 -> "Juggernaut";
            case 9 -> "Mirana";
            case 10 -> "Morphling";
            case 11 -> "Shadow Fiend";
            case 12 -> "Phantom Lancer";
            case 13 -> "Puck";
            case 14 -> "Pudge";
            case 15 -> "Razor";
            case 16 -> "Sand King";
            case 17 -> "Storm Spirit";
            case 18 -> "Sven";
            case 19 -> "Tiny";
            case 20 -> "Vengeful Spirit";
            case 21 -> "Windranger";
            case 22 -> "Zeus";
            case 23 -> "Kunkka";
            case 25 -> "Lina";
            case 26 -> "Lion";
            case 27 -> "Shadow Shaman";
            case 28 -> "Slardar";
            case 29 -> "Tidehunter";
            case 30 -> "Witch Doctor";
            case 31 -> "Lich";
            case 32 -> "Riki";
            case 33 -> "Enigma";
            case 34 -> "Tinker";
            case 35 -> "Sniper";
            case 36 -> "Necrophos";
            case 37 -> "Warlock";
            case 38 -> "Beastmaster";
            case 39 -> "Queen of Pain";
            case 40 -> "Venomancer";
            case 41 -> "Faceless Void";
            case 42 -> "Wraith King";
            case 43 -> "Death Prophet";
            case 44 -> "Phantom Assassin";
            case 45 -> "Pugna";
            case 46 -> "Templar Assassin";
            case 47 -> "Viper";
            case 48 -> "Luna";
            case 49 -> "Dragon Knight";
            case 50 -> "Dazzle";
            case 51 -> "Clockwerk";
            case 52 -> "Leshrac";
            case 53 -> "Nature's Prophet";
            case 54 -> "Lifestealer";
            case 55 -> "Dark Seer";
            case 56 -> "Clinkz";
            case 57 -> "Omniknight";
            case 58 -> "Enchantress";
            case 59 -> "Huskar";
            case 60 -> "Night Stalker";
            case 61 -> "Broodmother";
            case 62 -> "Bounty Hunter";
            case 63 -> "Weaver";
            case 64 -> "Jakiro";
            case 65 -> "Batrider";
            case 66 -> "Chen";
            case 67 -> "Spectre";
            case 68 -> "Ancient Apparition";
            case 69 -> "Doom";
            case 70 -> "Ursa";
            case 71 -> "Spirit Breaker";
            case 72 -> "Gyrocopter";
            case 73 -> "Alchemist";
            case 74 -> "Invoker";
            case 75 -> "Silencer";
            case 76 -> "Outworld Destroyer";
            case 77 -> "Lycan";
            case 78 -> "Brewmaster";
            case 79 -> "Shadow Demon";
            case 80 -> "Lone Druid";
            case 81 -> "Chaos Knight";
            case 82 -> "Meepo";
            case 83 -> "Treant Protector";
            case 84 -> "Ogre Magi";
            case 85 -> "Undying";
            case 86 -> "Rubick";
            case 87 -> "Disruptor";
            case 88 -> "Nyx Assassin";
            case 89 -> "Naga Siren";
            case 90 -> "Keeper of the Light";
            case 91 -> "Io";
            case 92 -> "Visage";
            case 93 -> "Slark";
            case 94 -> "Medusa";
            case 95 -> "Troll Warlord";
            case 96 -> "Centaur Warrunner";
            case 97 -> "Magnus";
            case 98 -> "Timbersaw";
            case 99 -> "Bristleback";
            case 100 -> "Tusk";
            case 101 -> "Skywrath Mage";
            case 102 -> "Abaddon";
            case 103 -> "Elder Titan";
            case 104 -> "Legion Commander";
            case 105 -> "Techies";
            case 106 -> "Ember Spirit";
            case 107 -> "Earth Spirit";
            case 108 -> "Underlord";
            case 109 -> "Terrorblade";
            case 110 -> "Phoenix";
            case 111 -> "Oracle";
            case 112 -> "Winter Wyvern";
            case 113 -> "Arc Warden";
            case 114 -> "Monkey King";
            case 119 -> "Dark Willow";
            case 120 -> "Pangolier";
            case 121 -> "Grimstroke";
            case 123 -> "Hoodwink";
            case 126 -> "Void Spirit";
            case 128 -> "Snapfire";
            case 129 -> "Mars";
            case 135 -> "Dawnbreaker";
            case 136 -> "Marci";
            case 137 -> "Primal Beast";
            case 138 -> "Muerta";
            default ->
                    throw new IllegalStateException("Unexpected value: " + heroIdParsed);
        };
        return heroName;
    }
    // refer to src/data-templates/code-key-templates/lobby_type.json
    //-------------------------Determine-Lobby-Type---------------------------
    public static String determineLobbyType(String lobbyTypeId) {
        int lobbyTypeNum = Integer.parseInt(lobbyTypeId);
        String lobbyType = switch (lobbyTypeNum) {
            case 0 -> "Normal";
            case 1 -> "Practice";
            case 2 -> "Tournament";
            case 3 -> "Tutorial";
            case 4 -> "Coop Bots";
            case 5 -> "Ranked Team";
            case 6 -> "Ranked Solo";
            case 7 -> "Ranked";
            case 8 -> "1v1 Mid";
            case 9 -> "Battle Cup";
            case 10 -> "Local Bots";
            case 11 -> "Spectator";
            case 12 -> "Event";
            case 13 -> "Gauntlet";
            case 14 -> "New Player";
            case 15 -> "Featured";
            default -> throw new IllegalStateException("Unexpected value: " + lobbyTypeNum);
        };
        return lobbyType;
    }
    // refer to src/data-templates/code-key-templates/game_mode.json
    //---------------------------Determine-Game-Mode--------------------------
    public static String determineGameMode(String gameModeId) {
        int gameModeIdNum = Integer.parseInt(gameModeId);
        String gameMode = switch (gameModeIdNum) {
            case 0 -> "Unknown";
            case 1 -> "All Pick";
            case 2 -> "Captains Mode";
            case 3 -> "Random Draft";
            case 4 -> "Single Draft";
            case 5 -> "All Random";
            case 6 -> "Intro";
            case 7 -> "Diretide";
            case 8 -> "Reverse Captains Mode";
            case 9 -> "Greeviling";
            case 10 -> "Tutorial";
            case 11 -> "Mid Only";
            case 12 -> "Least Played";
            case 13 -> "Limited Heroes";
            case 14 -> "Compendium Matchmaking";
            case 15 -> "Custom";
            case 16 -> "Captains Draft";
            case 17 -> "Balanced Draft";
            case 18 -> "Ability Draft";
            case 19 -> "Event";
            case 20 -> "All Random Deathmatch";
            case 21 -> "1v1 Mid";
            case 22 -> "All Draft";
            case 23 -> "Turbo";
            case 24 -> "Mutation";
            case 25 -> "Coaches Challenge";
            default -> throw new IllegalStateException("Unexpected value: " + gameModeIdNum);
        };
        return gameMode;
    }
    //------------------------------Determine-Rank----------------------------
    public static String determineRank(String rankId) {
        if (rankId.equals("null")) {
            return "null";
        }
        int medalNum = Integer.parseInt(String.valueOf(rankId.charAt(0)));
        String medal = switch (medalNum) {
            case 1 -> "Herald";
            case 2 -> "Guardian";
            case 3 -> "Crusader";
            case 4 -> "Archon";
            case 5 -> "Legend";
            case 6 -> "Ancient";
            case 7 -> "Divine";
            case 8 -> "Immortal";
            default -> throw new IllegalStateException("Unexpected value: " + medalNum);
        };
        String rank = "";
        String star = String.valueOf(rankId.charAt(1));
        if (medalNum == 8){
            rank = medal;
        } else {
            rank = medal + " " + star;
        }
        return rank;
    }
    // refer to src/data-templates/code-key-templates/patch.json
    //------------------------Determine-Version/Patch-------------------------
    public static String determineVersion(String versionId) {
        if (versionId.equals("null")) {
            return "null";
        }
        int versionIdNum = Integer.parseInt(versionId);
        String version = switch (versionIdNum) {
            case 0 -> "6.70";
            case 1 -> "6.71";
            case 2 -> "6.72";
            case 3 -> "6.73";
            case 4 -> "6.74";
            case 5 -> "6.75";
            case 6 -> "6.76";
            case 7 -> "6.77";
            case 8 -> "6.78";
            case 9 -> "6.79";
            case 10 -> "6.80";
            case 11 -> "6.81";
            case 12 -> "6.82";
            case 13 -> "6.83";
            case 14 -> "6.84";
            case 15 -> "6.85";
            case 16 -> "6.86";
            case 17 -> "6.87";
            case 18 -> "6.88";
            case 19 -> "7.00";
            case 20 -> "7.01";
            case 21 -> "7.02";
            case 22 -> "7.03";
            case 23 -> "7.04";
            case 24 -> "7.05";
            case 25 -> "7.06";
            case 26 -> "7.07";
            case 27 -> "7.08";
            case 28 -> "7.09";
            case 29 -> "7.10";
            case 30 -> "7.11";
            case 31 -> "7.12";
            case 32 -> "7.13";
            case 33 -> "7.14";
            case 34 -> "7.15";
            case 35 -> "7.16";
            case 36 -> "7.17";
            case 37 -> "7.18";
            case 38 -> "7.19";
            case 39 -> "7.20";
            case 40 -> "7.21";
            case 41 -> "7.22";
            case 42 -> "7.23";
            case 43 -> "7.24";
            case 44 -> "7.25";
            case 45 -> "7.26";
            case 46 -> "7.27";
            case 47 -> "7.28";
            case 48 -> "7.29";
            case 49 -> "7.30";
            case 50 -> "7.31";
            case 51 -> "7.32";
            case 52 -> "7.33";
            default -> throw new IllegalStateException("Unexpected value: " + versionIdNum);
        };
        return version;
    }
    public static String determineStartTime(String epochTime) {
        long timestamp = Long.parseLong(epochTime);
        Instant instant = Instant.ofEpochSecond(timestamp);
        LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(dateFormatter);
    }
}

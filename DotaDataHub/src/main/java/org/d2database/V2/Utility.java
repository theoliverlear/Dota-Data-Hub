package org.d2database.V2;

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
}

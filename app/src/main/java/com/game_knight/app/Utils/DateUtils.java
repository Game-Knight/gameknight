package com.game_knight.app.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String formatDate(LocalDateTime date) {
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("@ h:mm");
        String monthString = date.format(monthFormatter);
        String dayString = ordinalDate(date);
        String timeString = date.format(timeFormatter);
        return String.format("%s %s %s", monthString, dayString, timeString);
    }

    public static String ordinalDate(LocalDateTime date) {
        return ordinal(date.getDayOfMonth());
    }

    /* Given a number, return it's ordinal string */
    public static String ordinal(int i) {
        String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];

        }
    }
}

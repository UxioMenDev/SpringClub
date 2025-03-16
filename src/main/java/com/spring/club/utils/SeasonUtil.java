package com.spring.club.utils;

import java.util.Calendar;

public class SeasonUtil {
    public static String getCurrentSeason() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int first = currentYear % 100;
        int second = first + 1;
        return String.format("%d/%d", first, second);
    }
}
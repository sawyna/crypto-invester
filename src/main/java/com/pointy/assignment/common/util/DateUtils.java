package com.pointy.assignment.common.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public static final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}$";

    public static String currentDate() {
        return LocalDate.now().toString();
    }

    public static long dateInEpochSeconds(String dateString) {
        return DateTime.parse(dateString).getMillis() / 1000;
    }

    public static String nextDate(String dateString) {
        return nextDate(dateString, 1);
    }

    public static String nextDate(String dateString, int days) {
        return LocalDate.parse(dateString).plusDays(days).toString();
    }

    public static boolean isValidDateString(String dateString) {
        if (dateString == null || !dateString.matches(DATE_REGEX)) {
            LOGGER.info("Invalid date String {}", dateString);
            return false;
        }

        try {
            LocalDate.parse(dateString);
        } catch (Throwable e) {
            LOGGER.info("Invalid date String {}", dateString);
            return false;
        }
        return true;
    }
}

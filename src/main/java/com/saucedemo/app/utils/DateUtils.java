package com.saucedemo.app.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface DateUtils {
    public static String getTimestamp() {
        // Pattern: Year-Month-Day_Hour-Minute-Second-Milliseconds
        return getTimestamp("yyyy-MM-dd_HH-mm-ss-SSS");
    }

    public static String getTimestamp(String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return dtf.format(LocalDateTime.now());
    }
}

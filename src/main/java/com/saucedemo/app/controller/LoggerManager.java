package com.saucedemo.app.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerManager {
    private static final ThreadLocal<Logger> loggerThreadLocal = ThreadLocal.withInitial(() -> {
        try {
            return LogManager.getLogger(Thread.currentThread().getName());
        } catch (Exception e) {
            System.err.println("Logger initialization failed: " + e.getMessage());
            return null;
        }
    });

    public static Logger getLogger() {
        return loggerThreadLocal.get();
    }

    public static void info(String message) {
        getLogger().info(message);
    }

    public static void error(String message) {
        getLogger().error(message);
    }

    public static void debug(String message) {
        getLogger().debug(message);
    }
}

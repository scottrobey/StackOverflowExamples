package org.robey.utils;

import java.util.Date;

/**
 * A very crude logger just to keep things simple for the code examples
 */
public class Log {

    public static void log(String message) {
        System.out.println("[" + new Date() + "] [" + Thread.currentThread().getName() + "] " + message);

    }

    public static void logError(String message) {
        System.err.println("[" + new Date() + "] [" + Thread.currentThread().getName() + "] " + message);
    }

    public static void logError(String message, Throwable exception) {
        logError(message + " Error: " + exception.getMessage());
        exception.printStackTrace();
    }
}

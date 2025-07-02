package io.github.railroad.railroadpluginapi.log;

/**
 * Enum representing the different logging levels.
 * Used to categorize log messages based on their severity.
 */
public enum LoggingLevel {
    /**
     * Indicates a serious failure that prevents the application from continuing.
     */
    ERROR,
    /**
     * Indicates a warning that may require attention but does not prevent the application from running.
     */
    WARN,
    /**
     * Indicates general information about the application's operation.
     */
    INFO,
    /**
     * Indicates detailed information useful for debugging purposes.
     */
    DEBUG
}

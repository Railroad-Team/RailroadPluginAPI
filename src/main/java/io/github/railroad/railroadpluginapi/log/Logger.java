package io.github.railroad.railroadpluginapi.log;

/**
 * Interface for a logging system that provides methods to log messages at different levels.
 * Implementations of this interface should handle the actual logging mechanism.
 */
public interface Logger {
    /**
     * Logs an error message with the specified objects.
     *
     * @param logMessage The error message to log.
     * @param objects    Additional objects to include in the log message.
     */
    void error(String logMessage, Object... objects);

    /**
     * Logs a warning message with the specified objects.
     *
     * @param logMessage The warning message to log.
     * @param objects    Additional objects to include in the log message.
     */
    void warn(String logMessage, Object... objects);

    /**
     * Logs an informational message with the specified objects.
     *
     * @param logMessage The informational message to log.
     * @param objects    Additional objects to include in the log message.
     */
    void info(String logMessage, Object... objects);

    /**
     * Logs a debug message with the specified objects.
     *
     * @param logMessage The debug message to log.
     * @param objects    Additional objects to include in the log message.
     */
    void debug(String logMessage, Object... objects);

    /**
     * Logs a message with the specified logging level and objects.
     *
     * @param message The message to log.
     * @param level   The logging level.
     * @param objects Additional objects to include in the log message.
     */
    void log(String message, LoggingLevel level, Object... objects);
}

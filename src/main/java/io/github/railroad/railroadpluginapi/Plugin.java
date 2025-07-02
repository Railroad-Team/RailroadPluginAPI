package io.github.railroad.railroadpluginapi;

/**
 * Railroad Plugin API
 * <p>
 * This interface defines the basic structure of a plugin in the Railroad system.
 * Plugins can be initialized, started, and stopped, allowing for dynamic behavior
 * within the Railroad application.
 */
public interface Plugin {
    /**
     * Initializes the plugin with the provided context.
     * The context provides access to the plugin's descriptor, event bus, logger,
     * and methods for registering extensions.
     *
     * @param context the plugin context
     */
    void initialize(PluginContext context);

    /**
     * Starts the plugin.
     * This method is called after the plugin has been initialized and is ready to
     * perform its operations.
     */
    void start();

    /**
     * Stops the plugin.
     * This method is called when the plugin is no longer needed or when the application
     * is shutting down. It allows the plugin to clean up resources and perform any
     * necessary shutdown operations.
     */
    void stop();
}

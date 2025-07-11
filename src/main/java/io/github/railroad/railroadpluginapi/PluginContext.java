package io.github.railroad.railroadpluginapi;

import io.github.railroad.logger.Logger;
import io.github.railroad.railroadpluginapi.event.EventBus;

import java.nio.file.Path;
import java.util.List;

/*
 * Railroad Plugin API
 *
 * This interface defines the context in which a plugin operates, providing access to
 * the plugin's descriptor, event bus, logger, and methods for registering extensions.
 */
public interface PluginContext {
    /**
     * Returns the descriptor of the plugin.
     * The descriptor contains metadata about the plugin, such as its name, version,
     * description, and other relevant information.
     *
     * @return the plugin descriptor
     */
    PluginDescriptor getDescriptor();

    /**
     * Returns the event bus used for communication between plugins and the core system.
     * The event bus allows plugins to publish and subscribe to events, facilitating
     * decoupled communication.
     *
     * @return the event bus
     */
    EventBus getEventBus();

    /**
     * Returns the logger for the plugin.
     * The logger is used for logging messages, warnings, and errors within the plugin.
     *
     * @return the logger
     */
    Logger getLogger();

    /**
     * Registers an extension for the specified extension point.
     * Extensions are additional functionalities or features that can be added to the plugin.
     *
     * @param <T>            the type of the extension
     * @param extensionPoint the class representing the extension point
     * @param extension      the extension to register
     */
    <T> void registerExtension(Class<T> extensionPoint, T extension);

    /**
     * Retrieves all registered extensions for the specified extension point.
     * This method returns a list of all extensions that have been registered
     * for the given extension point class.
     *
     * @param <T>            the type of the extension
     * @param extensionPoint the class representing the extension point
     * @return a list of registered extensions for the specified extension point
     */
    <T> List<T> getExtensions(Class<T> extensionPoint);

    /**
     * Returns the directory where the plugin's data is stored.
     * This directory is typically used for storing configuration files, logs, and other
     * persistent data related to the plugin.
     *
     * @return the data directory path
     */
    Path getDataDirectory();

    /**
     * Retrieves a service of the specified class from the plugin context.
     * This method allows plugins to access shared services provided by the core system
     * or other plugins.
     *
     * @param <T>          the type of the service
     * @param serviceClass the class representing the service
     * @return an instance of the requested service, or null if not found
     */
    <T> T getService(Class<T> serviceClass);
}

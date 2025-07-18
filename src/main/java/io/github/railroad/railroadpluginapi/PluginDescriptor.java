package io.github.railroad.railroadpluginapi;

import java.util.Map;

/**
 * Represents the metadata of a plugin.
 * This interface defines the methods that must be implemented by any plugin descriptor.
 */
public interface PluginDescriptor {
    /**
     * Returns the unique identifier of the plugin.
     *
     * @return the plugin ID
     */
    String getId();

    /**
     * Returns the name of the plugin.
     *
     * @return the plugin name
     */
    String getName();

    /**
     * Returns the version of the plugin.
     *
     * @return the plugin version
     */
    String getVersion();

    /**
     * Returns the author of the plugin.
     *
     * @return the plugin author
     */
    String getAuthor();

    /**
     * Returns a brief description of the plugin.
     *
     * @return the plugin description
     */
    String getDescription();

    /**
     * Returns the website URL of the plugin.
     *
     * @return the plugin website URL
     */
    String getWebsite();

    /**
     * Returns the license of the plugin.
     *
     * @return the plugin license
     */
    String getLicense();

    /**
     * Returns the path to the icon of the plugin.
     *
     * @return the plugin icon path
     */
    String getIconPath();

    /**
     * Returns the main class of the plugin.
     *
     * @return the main class name
     */
    String getMainClass();

    /**
     * Returns a map of dependencies required by the plugin.
     * The keys are the dependency names and the values are the versions.
     *
     * @return a map of dependencies
     */
    Map<String, String> getDependencies();
}

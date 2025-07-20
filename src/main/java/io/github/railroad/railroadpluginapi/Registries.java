package io.github.railroad.railroadpluginapi;

import io.github.railroad.core.registry.Registry;
import io.github.railroad.core.settings.Setting;

/**
 * Utility class to access various registries in the Railroad plugin API.
 * This class provides methods to retrieve specific registries, such as the settings registry.
 */
public class Registries {
    /**
     * Retrieves the settings registry from the provided PluginContext.
     *
     * @param context The PluginContext from which to retrieve the settings registry.
     * @return The settings registry containing Setting objects.
     * @throws IllegalArgumentException if the provided context is null.
     * @throws IllegalStateException if the settings registry is not available in the context.
     */
    public static Registry<Setting<?>> getSettingsRegistry(PluginContext context) {
        if (context == null)
            throw new IllegalArgumentException("PluginContext cannot be null");

        @SuppressWarnings("unchecked")
        Registry<Setting<?>> settingsRegistry = (Registry<Setting<?>>) context.getRegistry("settings");
        if (settingsRegistry == null)
            throw new IllegalStateException("Settings registry is not available in the provided PluginContext");

        return settingsRegistry;
    }
}

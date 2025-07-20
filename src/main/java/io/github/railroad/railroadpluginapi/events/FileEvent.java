package io.github.railroad.railroadpluginapi.events;

import io.github.railroad.railroadpluginapi.dto.Document;
import io.github.railroad.railroadpluginapi.event.Event;

/**
 * Represents an event related to file operations within the Railroad IDE.
 * This event is used to notify subscribers about various file-related actions such as opening, closing,
 * saving, modifying, deleting, renaming, activating, and deactivating files.
 */
public record FileEvent(Document file, FileEvent.EventType eventType) implements Event {
    /**
     * Constructs a new FileEvent.
     *
     * @param file  The file associated with this event. Must not be null.
     * @param eventType The type of file event (e.g., OPENED, CLOSED, SAVED, etc.). Must not be null.
     * @throws IllegalArgumentException if file or eventType is null.
     */
    public FileEvent {
        if (file == null)
            throw new IllegalArgumentException("file cannot be null");

        if (eventType == null)
            throw new IllegalArgumentException("eventType cannot be null");

    }

    /**
     * Checks if the event is related to a file being opened.
     *
     * @return true if the event type is OPENED, false otherwise.
     */
    public boolean isOpened() {
        return eventType == EventType.OPENED;
    }

    /**
     * Checks if the event is related to a file being closed.
     *
     * @return true if the event type is CLOSED, false otherwise.
     */
    public boolean isClosed() {
        return eventType == EventType.CLOSED;
    }

    /**
     * Checks if the event is related to a file being saved.
     *
     * @return true if the event type is SAVED, false otherwise.
     */
    public boolean isSaved() {
        return eventType == EventType.SAVED;
    }

    /**
     * Checks if the event is related to a file being deleted.
     *
     * @return true if the event type is DELETED, false otherwise.
     */
    public boolean isDeleted() {
        return eventType == EventType.DELETED;
    }

    /**
     * Checks if the event is related to a file being activated.
     *
     * @return true if the event type is ACTIVATED, false otherwise.
     */
    public boolean isActivated() {
        return eventType == EventType.ACTIVATED;
    }

    /**
     * Checks if the event is related to a file being deactivated.
     *
     * @return true if the event type is DEACTIVATED, false otherwise.
     */
    public boolean isDeactivated() {
        return eventType == EventType.DEACTIVATED;
    }

    /**
     * Enum representing the different types of file events.
     */
    public enum EventType {
        OPENED,
        CLOSED,
        SAVED,
        DELETED,
        ACTIVATED,
        DEACTIVATED
    }
}

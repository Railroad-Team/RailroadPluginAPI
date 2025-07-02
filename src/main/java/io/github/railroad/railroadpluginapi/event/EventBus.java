package io.github.railroad.railroadpluginapi.event;

/**
 * An interface for an event bus that allows publishing events and subscribing to them.
 */
public interface EventBus {
    /**
     * Publishes an event to the event bus.
     *
     * @param event the event to publish
     */
    void publish(Event event);

    /**
     * Subscribes a listener to events of a specific type.
     *
     * @param eventType the class of the event type to subscribe to
     * @param listener  the listener that will handle the events
     * @param <T>       the type of the event
     */
    <T extends Event> void subscribe(Class<T> eventType, EventListener<T> listener);
}

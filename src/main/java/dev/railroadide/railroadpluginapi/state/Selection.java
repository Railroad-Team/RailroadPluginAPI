package dev.railroadide.railroadpluginapi.state;

/**
 * Represents a text selection in the text editor, defined by a start and end cursor.
 *
 * @param start The starting cursor of the selection.
 * @param end   The ending cursor of the selection.
 */
public record Selection(Cursor start, Cursor end) {
}

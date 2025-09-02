package dev.railroadide.railroadpluginapi.services;

import dev.railroadide.railroadpluginapi.state.Cursor;
import dev.railroadide.railroadpluginapi.state.Selection;

import java.util.List;

/**
 * Service interface for accessing the state of the current document editor.
 * This includes information about cursor positions, text selections, and the language of the document.
 */
public interface DocumentEditorStateService {
    /**
     * Returns a list of all cursor positions in the current text editor.
     *
     * @return a list of {@link Cursor} objects representing cursor positions
     */
    List<Cursor> getCursors();

    /**
     * Returns a list of all text selections in the current text editor.
     *
     * @return a list of {@link Selection} objects representing selected text ranges
     */
    List<Selection> getSelections();

    /**
     * Returns the language identifier of the current document.
     *
     * @return the language ID as a {@link String}
     */
    String getLanguageId();
}

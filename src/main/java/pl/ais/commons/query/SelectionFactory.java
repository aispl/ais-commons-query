package pl.ais.commons.query;

import java.io.Serializable;

/**
 * Defines the API contract for factory creating the {@link Selection selections}.
 *
 * @param <R> the type of ordering element
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public interface SelectionFactory<R extends Serializable> {

    /**
     * Creates and returns new {@link Selection} instance.
     *
     * @param startIndex the index of first record
     * @param displayLength the display length ({@code -1} when all records should be selected)
     * @param orderings orderings which should be used by this selection
     * @return newly created {@link Selection} instance
     */
    Selection<R> createSelection(int startIndex, int displayLength, R... orderings);

}

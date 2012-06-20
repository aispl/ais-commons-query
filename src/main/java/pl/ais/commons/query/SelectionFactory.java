package pl.ais.commons.query;

import java.io.Serializable;

/**
 * Defines the API contract for factory creating the {@link Selection selections}.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public interface SelectionFactory {

    /**
     * Creates and returns new {@link Selection} instance.
     *
     * @param startIndex the index of first record
     * @param displayLength the display length ({@code -1} when all records should be selected)
     * @param orderings orderings which should be used by this selection
     * @return newly created {@link Selection} instance
     */
    <R extends Serializable> Selection createSelection(int startIndex, int displayLength, R... orderings);

}

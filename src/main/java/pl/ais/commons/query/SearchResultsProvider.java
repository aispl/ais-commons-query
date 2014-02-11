package pl.ais.commons.query;

import java.io.Serializable;

/**
 * Defines the API contract for providing {@link SearchResults} for the specific {@link Selection}.
 *
 * @param <E> the type of each search result
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public interface SearchResultsProvider<E extends Serializable> {

    /**
     * Provides {@link SearchResults} for given selection.
     *
     * @param selection determines which of the matching users should be fetched and how they should be ordered
     * @return {@link SearchResults} determined by given selection
     */
    SearchResults<E> provideForSelection(Selection<?> selection);

}

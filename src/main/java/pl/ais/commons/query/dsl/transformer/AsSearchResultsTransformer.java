package pl.ais.commons.query.dsl.transformer;

import java.io.Serializable;

import pl.ais.commons.query.SearchResults;

import com.mysema.query.Projectable;
import com.mysema.query.ResultTransformer;
import com.mysema.query.types.Expression;

/**
 * {@link ResultTransformer} implementation transforming query results into {@link SearchResults}.
 *
 * @param <T> the type of elements in the search results
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
final class AsSearchResultsTransformer<T extends Serializable> implements ResultTransformer<SearchResults<T>> {

    private final Expression<T> projection;

    /**
     * @param projection
     */
    public AsSearchResultsTransformer(final Expression<T> projection) {
        this.projection = projection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResults<T> transform(final Projectable projectable) {
        final long totalRecords = projectable.count();
        final SearchResults<T> searchResults;
        if (0 < totalRecords) {
            searchResults = new SearchResults<>(projectable.list(projection),
                totalRecords);
        } else {
            searchResults = SearchResults.emptySearchResults();
        }
        return searchResults;
    }

}
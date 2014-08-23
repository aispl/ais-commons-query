package pl.ais.commons.query.dsl.transformer;

import com.mysema.query.types.Expression;
import pl.ais.commons.query.SearchResults;
import pl.ais.commons.query.dsl.ResultTransformer;

import java.io.Serializable;
import java.util.List;

/**
 * Provides set of utility methods for creating result transformers.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
public final class Transformers {

    /**
     * Constructs new instance.
     */
    private Transformers() {
        super();
    }

    /**
     * Creates {@link ResultTransformer} transforming query results into {@link List}.
     *
     * @param projection results projection
     * @return newly created transformer instance
     */
    public static <T> ResultTransformer<List<T>> list(final Expression<T> projection) {
        return new AsListTransformer<>(projection);
    }

    /**
     * Creates {@link ResultTransformer} returning number of results.
     *
     * @return newly created transformer instance
     */
    public static ResultTransformer<Long> numberOfResults() {
        return new AsNumberOfResults();
    }

    /**
     * Creates {@link ResultTransformer} transforming query results into {@link SearchResults}.
     *
     * @param projection results projection
     * @return newly created transformer instance
     */
    public static <T extends Serializable> ResultTransformer<SearchResults<T>> searchResults(
        final Expression<T> projection) {
        return new AsSearchResultsTransformer<>(projection);
    }

    /**
     * Creates {@link ResultTransformer} transforming query results into single result.
     *
     * @param projection results projection
     * @return newly created transformer instance
     */
    public static <T> ResultTransformer<T> singleResult(final Expression<T> projection) {
        return new AsSingleResultTransformer<>(projection);
    }

}

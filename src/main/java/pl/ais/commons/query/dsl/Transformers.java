package pl.ais.commons.query.dsl;

import com.mysema.commons.lang.CloseableIterator;
import com.querydsl.core.QueryResults;
import com.querydsl.core.ResultTransformer;
import com.querydsl.core.types.Expression;
import pl.ais.commons.query.SearchResults;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
     * Provides {@link ResultTransformer} returning first result of the query execution.
     *
     * @param projection results projection
     * @param <T>        the type of returned result
     * @return {@link ResultTransformer} returning first result of the query execution, or {@code null} if query
     * execution returned no results
     */
    public static <T> ResultTransformer<T> firstResult(final Expression<T> projection) {
        return query -> query.select(projection).fetchFirst();
    }

    /**
     * Provides {@link ResultTransformer} returning a {@link List} holding all results of the query execution.
     *
     * @param projection results projection
     * @return {@link ResultTransformer} returning a {@link List} holding all results of the query execution
     */
    public static <T> ResultTransformer<List<T>> list(final Expression<T> projection) {
        return query -> query.select(projection).fetch();
    }

    /**
     * Provides {@link ResultTransformer} returning number of records matching the query.
     *
     * @return {@link ResultTransformer} returning number of records matching the query
     */
    public static ResultTransformer<Long> numberOfResults() {
        return query -> query.fetchCount();
    }

    /**
     * Provides {@link ResultTransformer} returning {@link SearchResults} holding query execution results.
     *
     * @param projection results projection
     * @param <T>        the type of returned results
     * @return {@link ResultTransformer} returning {@link SearchResults} holding query execution results
     */
    public static <T extends Serializable> ResultTransformer<SearchResults<T>> searchResults(
        final Expression<T> projection) {
        return query -> {
            final QueryResults<T> queryResults = query.select(projection).fetchResults();
            return SearchResults.of(queryResults.getTotal(), queryResults.getResults());
        };
    }

    /**
     * Provides {@link ResultTransformer} returning single query execution result.
     *
     * @param projection results projection
     * @param <T>        the type of returned result
     * @return {@link ResultTransformer} returning single query execution result.
     * @throws com.querydsl.core.NonUniqueResultException if there are more than one result of the query execution
     */
    public static <T> ResultTransformer<T> singleResult(final Expression<T> projection) {
        return query -> query.select(projection).fetchOne();
    }

    /**
     * Provides {@link ResultTransformer} returning stream of query execution results.
     *
     * @param projection results projection
     * @param <T>        the type of returned results
     * @return {@link ResultTransformer} returning stream of query execution results
     */
    public static <T> ResultTransformer<Stream<T>> stream(final Expression<T> projection) {
        return query -> {
            final CloseableIterator<T> iterator = query.select(projection).iterate();
            final Iterable<T> iterable = () -> iterator;
            return StreamSupport.stream(iterable.spliterator(), false)
                                .onClose(() -> iterator.close());
        };
    }

}

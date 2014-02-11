package pl.ais.commons.query.dsl;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import pl.ais.commons.query.Selection;

import com.mysema.query.Projectable;
import com.mysema.query.ResultTransformer;
import com.mysema.query.SimpleQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

/**
 * Utility class for manipulating query results.
 *
 * @param <Q> determines query type
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@NotThreadSafe
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class Results<Q extends Projectable & SimpleQuery<Q>> {

    /**
     * Returns new {@link Results} instance for given query.
     *
     * @param query query determining the results
     * @return newly created {@link Results} instance for given query
     */
    public static <Q extends Projectable & SimpleQuery<Q>> Results<Q> forQuery(@Nonnull final Q query) {
        return new Results<>(query);
    }

    private Q query;

    /**
     * Constructs new instance for given query.
     *
     * @param query query determining the results
     */
    protected Results(@Nonnull final Q query) {
        super();

        // Verify constructor requirements, ...
        if (null == query) {
            throw new IllegalArgumentException("Query is required.");
        }

        // ... and initialize this instance fields.
        this.query = query;
    }

    /**
     * Narrows the results to those matching given predicate.
     *
     * @param predicate predicate which should be matched by desired results
     * @return the results
     */
    public Results<Q> matching(final Predicate predicate) {
        if (null != predicate) {
            query = query.where(predicate);
        }
        return this;
    }

    /**
     * Executes encapsulated query and transforms the results using given transformer.
     *
     * @param transformer results transformer to be used
     * @return transformed query results
     */
    public <T> T transform(final ResultTransformer<T> transformer) {
        return query.transform(transformer);
    }

    /**
     * Narrows the results using specified selection.
     *
     * @param selection determines which of the results will be fetched, and how they will be ordered
     * @return query determining the narrowed results
     */
    public Results<Q> within(final Selection<OrderSpecifier<?>> selection) {
        if (selection.isSelectingSubset()) {
            query = query.offset(selection.getStartIndex()).limit(selection.getDisplayLength());
        }
        query = query.orderBy(selection.getOrderings().toArray(new OrderSpecifier<?>[0]));
        return this;
    }

}

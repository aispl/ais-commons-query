package pl.ais.commons.query.dsl;

import com.mysema.query.Projectable;
import com.mysema.query.support.ProjectableQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import pl.ais.commons.query.Selection;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * Utility class for manipulating query results.
 *
 * @param <Q> determines query type
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@NotThreadSafe
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class Results<Q extends ProjectableQuery<Q>> {

    private Q query;

    private Selection<OrderSpecifier<?>> selection;

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
     * Returns new {@link Results} instance for given query.
     *
     * @param query query determining the results
     * @return newly created {@link Results} instance for given query
     */
    public static <Q extends ProjectableQuery<Q>> Results<Q> forQuery(@Nonnull final Q query) {
        return new Results<>(query);
    }

    /**
     * Executes encapsulated query and transforms the results using given transformer.
     *
     * @param transformer results transformer to be used
     * @return transformed query results
     */
    @SuppressWarnings("PMD.ShortMethodName")
    public <T> T as(final ResultTransformer<T> transformer) {

        return transformer.apply(new ProjectableSupplier() {

            @Override
            public Projectable get() {
                final Q result;
                if (null == selection) {
                    result = query;
                } else {
                    if (selection.isSelectingSubset()) {
                        query.offset(selection.getStartIndex()).limit(selection.getDisplayLength());
                    }
                    result = query.orderBy(selection.getOrderings().toArray(new OrderSpecifier<?>[0]));
                }
                return result;
            }
        });
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
     * Narrows the results using specified selection.
     *
     * @param selection determines which of the results will be fetched, and how they will be ordered
     * @return query determining the narrowed results
     */
    public Results<Q> within(final Selection<OrderSpecifier<?>> selection) {
        this.selection = selection;
        return this;
    }

}

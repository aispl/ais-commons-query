package pl.ais.commons.query.dsl;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

import javax.annotation.Nonnegative;

/**
 * Defines the API contract for the query delegate.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.3
 */
public interface QueryDelegate {

    /**
     * Defines the limit / max results for the query results
     *
     * @param limit max results to be fetched
     * @return the query delegate itself, for method invocation chaining
     */
    QueryDelegate limit(@Nonnegative long limit);

    /**
     * Defines the offset for the query results
     *
     * @param offset the ofset to be used
     * @return the query delegate itself, for method invocation chaining
     */
    QueryDelegate offset(long offset);

    /**
     * Add order expressions
     *
     * @param expressions the order expressions to add
     * @return the query delegate itself, for method invocation chaining
     */
    QueryDelegate orderBy(OrderSpecifier<?>... expressions);

    /**
     * @return the projectable to be used for fetching query results
     */
    ProjectableDelegate toProjectable();

    /**
     * Add the given filter condition.
     *
     * @param predicate filter condition to be added
     * @return the query delegate itself, for method invocation chaining
     */
    QueryDelegate where(Predicate predicate);

}

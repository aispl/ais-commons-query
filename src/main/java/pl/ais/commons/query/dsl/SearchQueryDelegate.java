package pl.ais.commons.query.dsl;

import com.mysema.query.SimpleProjectable;
import com.mysema.query.SimpleQuery;
import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;

import javax.annotation.Nonnegative;
import java.util.List;

/**
 * {@link QueryDelegate} implementation suitable for encapsulating {@link SimpleQuery} instances.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.3
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
final class SearchQueryDelegate<Q extends SimpleQuery<Q> & SimpleProjectable<?>> implements QueryDelegate {

    private final Q query;

    /**
     * Constructs new instance.
     *
     * @param query query being delegation target
     */
    SearchQueryDelegate(final Q query) {
        super();
        this.query = query;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QueryDelegate limit(@Nonnegative final long limit) {
        query.limit(limit);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QueryDelegate offset(final long offset) {
        query.offset(offset);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QueryDelegate orderBy(final OrderSpecifier<?>... expressions) {
        query.orderBy(expressions);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProjectableDelegate toProjectable() {
        return new ProjectableDelegate() {

            @Override
            public long count() {
                return query.count();
            }

            @Override
            public <R> List<R> list(final Expression<R> projection) {
                return (List<R>) query.list();
            }

            @Override
            public <R> R singleResult(final Expression<R> projection) {
                return (R) query.singleResult();
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QueryDelegate where(final Predicate predicate) {
        query.where(predicate);
        return this;
    }

}

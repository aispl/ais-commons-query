package pl.ais.commons.query.dsl.transformer;

import com.mysema.query.Projectable;
import com.mysema.query.ResultTransformer;
import com.mysema.query.types.Expression;

/**
 * {@link ResultTransformer} implementation transforming query results into single element (first one).
 *
 * @param <T> the type of returned element
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
final class AsSingleResultTransformer<T> implements ResultTransformer<T> {

    private final Expression<T> projection;

    /**
     * @param projection
     */
    public AsSingleResultTransformer(final Expression<T> projection) {
        this.projection = projection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T transform(final Projectable projectable) {
        return projectable.singleResult(projection);
    }

}

package pl.ais.commons.query.dsl.transformer;

import com.mysema.query.types.Expression;
import pl.ais.commons.query.dsl.ProjectableSupplier;
import pl.ais.commons.query.dsl.ResultTransformer;

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
    AsSingleResultTransformer(final Expression<T> projection) {
        this.projection = projection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T apply(final ProjectableSupplier projectable) {
        return projectable.get().singleResult(projection);
    }

}

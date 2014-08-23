package pl.ais.commons.query.dsl.transformer;

import com.mysema.query.types.Expression;
import pl.ais.commons.query.dsl.ProjectableSupplier;
import pl.ais.commons.query.dsl.ResultTransformer;

import java.util.List;

/**
 * {@link ResultTransformer} implementation transforming query results into {@link List}.
 *
 * @param <T> the type of elements in the list
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
final class AsListTransformer<T> implements ResultTransformer<List<T>> {

    private final Expression<T> projection;

    /**
     * @param projection
     */
    AsListTransformer(final Expression<T> projection) {
        this.projection = projection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> apply(final ProjectableSupplier projectable) {
        return projectable.get().list(projection);
    }

}

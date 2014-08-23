package pl.ais.commons.query.dsl.transformer;

import pl.ais.commons.query.dsl.ProjectableSupplier;
import pl.ais.commons.query.dsl.ResultTransformer;

/**
 * {@link ResultTransformer} returning number of results.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
final class AsNumberOfResults implements ResultTransformer<Long> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Long apply(final ProjectableSupplier projectable) {
        return Long.valueOf(projectable.get().count());
    }

}

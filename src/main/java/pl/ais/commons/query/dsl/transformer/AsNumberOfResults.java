package pl.ais.commons.query.dsl.transformer;

import com.mysema.query.Projectable;
import com.mysema.query.ResultTransformer;

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
    public Long transform(final Projectable projectable) {
        return Long.valueOf(projectable.count());
    }

}

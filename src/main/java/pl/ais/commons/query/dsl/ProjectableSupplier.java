package pl.ais.commons.query.dsl;

import com.mysema.query.Projectable;

/**
 * Defines the API contract for projectable supplier.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.3
 */
public interface ProjectableSupplier {

    /**
     * @return query
     */
    Projectable get();

}

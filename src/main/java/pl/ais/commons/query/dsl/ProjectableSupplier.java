package pl.ais.commons.query.dsl;

/**
 * Defines the API contract for projectable supplier.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.3
 */
public interface ProjectableSupplier {

    /**
     * @return the projectable instance
     */
    ProjectableDelegate get();

}

package pl.ais.commons.query.dsl;

/**
 * Defines the API contract for query result transformer.
 *
 * @param <R> defines query result transformation type
 * @author Warlock, AIS.PL
 * @since 1.1.3
 */
public interface ResultTransformer<R> {

    /**
     * Applies query result transformation.
     *
     * @param projectable projectable supplier
     * @return transformed query results
     */
    R apply(ProjectableSupplier projectable);

}

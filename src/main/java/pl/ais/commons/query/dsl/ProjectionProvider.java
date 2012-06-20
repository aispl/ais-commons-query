package pl.ais.commons.query.dsl;

import java.io.Serializable;
import java.util.List;

import com.mysema.query.Projectable;

/**
 * Defines the API contract for providing list over the {@link Projectable projectable} query results.
 *
 * @param <T> determines the type of projection records returned by the provider
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public interface ProjectionProvider<T extends Serializable> {

    /**
     * Executes provided {@link Projectable projectable} query and returns the list over the projection.
     *
     * @param query {@link Projectable projectable} query which should be executed
     * @return list over the {@link Projectable projectable} query results
     */
    List<T> provideForQuery(Projectable query);

}

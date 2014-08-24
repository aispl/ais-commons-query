package pl.ais.commons.query.dsl;

import com.mysema.query.types.Expression;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.List;

/**
 * Defines the API contract for the projectable.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.3
 */
public interface ProjectableDelegate {

    /**
     * @return the amount of matched rows.
     */
    @Nonnegative
    long count();

    /**
     * @param <R>        generic type of the List
     * @param projection projection
     * @return a list over the projection
     */
    @Nonnull
    <R> List<R> list(Expression<R> projection);

    /**
     * @param <R>        return type
     * @param projection projection
     * @return (first) result (or {@code null} for an empty result)
     */
    <R> R singleResult(Expression<R> projection);

}

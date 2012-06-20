package pl.ais.commons.query;

import javax.annotation.CheckForNull;

/**
 * Defines the API contract for predicate precursor.
 *
 * @param <T> determines the type of returned predicate
 *
 * @author Warlock, AIS.PL
 * @since 1.0
 */
public interface PredicatePrecursor<T> {

    /**
     * Returns the predicate.
     *
     * @return the predicate (can be {@code null})
     */
    @CheckForNull
    T toPredicate();

}

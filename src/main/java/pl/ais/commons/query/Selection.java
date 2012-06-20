package pl.ais.commons.query;

import java.io.Serializable;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Defines the subset of database records by specifying the records ordering, index of the first one,
 * and the desired number of records.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public interface Selection extends Serializable {

    /**
     * @return the display length ({@code -1} when all records should be selected)
     * @see {@link #isSelectingSubset()}
     */
    int getDisplayLength();

    /**
     * @return current orderings used by this selection
     */
    @Nonnull
    <R extends Serializable> R[] getOrderings();

    /**
     * @return the index of first record
     */
    @Nonnegative
    int getStartIndex();

    /**
     * @return {@code true} if subset of all records (defined by this selection) should be selected,
     *         {@code false} otherwise (all records should be selected)
     */
    boolean isSelectingSubset();

    /**
     * Creates and returns new {@link Selection} instance by merging current and provided data ordering.
     *
     * @param orderings the orderings to add
     * @return newly created {@link Selection} instance
     */
    @Nonnull
    <R extends Serializable> Selection withOrderings(@Nonnull final R... orderings);

}
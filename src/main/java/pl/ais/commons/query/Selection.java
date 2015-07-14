package pl.ais.commons.query;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;

/**
 * Defines the subset of database records by specifying the records ordering, index of the first one,
 * and the desired number of records.
 *
 * @param <R> the type of ordering
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public interface Selection<R> {

    /**
     * @return the display length ({@code -1} when all records should be selected)
     * @see {@link #isSelectingSubset()}
     */
    int getDisplayLength();

    /**
     * @return current orderings used by this selection
     */
    @Nonnull
    R[] getOrderings();

    /**
     * @return the index of first record
     */
    @Nonnegative
    int getStartIndex();

    /**
     * @return {@code true} if subset of all records (defined by this selection) should be selected,
     * {@code false} otherwise (all records should be selected)
     */
    boolean isSelectingSubset();

    /**
     * Creates and returns new {@link Selection} instance by merging current and provided data ordering.
     *
     * @param first first ordering to be merged
     * @param rest  remaining orderings to be merged
     * @return newly created {@link Selection} instance
     */
    @Nonnull
    default Selection<R> withOrderings(@Nonnull R first, @Nonnull R... rest) {
        final ArrayDeque<R> queue = new ArrayDeque<>(rest.length + 1);
        queue.addFirst(first);
        queue.addAll(Arrays.asList(rest));
        return withOrderings(queue);
    }

    /**
     * Creates and returns new {@link Selection} instance by merging current and provided data ordering.
     *
     * @param orderings collection of orderings to be merged
     * @return newly created {@link Selection} instance
     */
    Selection<R> withOrderings(@Nonnull Collection<R> orderings);

}
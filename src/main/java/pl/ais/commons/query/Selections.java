package pl.ais.commons.query;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Defines commonly used set of {@link Selection selections}.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class Selections {

    /**
     * Constructs new instance.
     */
    private Selections() {
        super();
    }

    /**
     * Creates and returns {@link Selection} instance selecting all records.
     *
     * @param factory   the factory which will be used to create Selection
     * @param orderings orderings which should be used by this selection
     * @return newly created {@link Selection} instance
     */
    @SuppressWarnings("unchecked")
    @Nonnull
    public static <R, S extends Selection<R>> S allRecords(
        @Nonnull final SelectionFactory<R, S> factory, final R... orderings) {
        return factory.createSelection(0, -1, orderings);
    }

    /**
     * Creates and returns {@link Selection} instance selecting specified records.
     *
     * @param startIndex    the index of first record
     * @param displayLength the display length ({@code -1} when all records should be selected)
     * @param factory       the factory which will be used to create Selection
     * @param orderings     orderings which should be used by this selection
     * @return newly created {@link Selection} instance
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <R, S extends Selection<R>> S slice(
        @Nonnegative final int startIndex, final int displayLength, @Nonnull final SelectionFactory<R, S> factory,
        final R... orderings) {
        return factory.createSelection(startIndex, displayLength, orderings);
    }

}

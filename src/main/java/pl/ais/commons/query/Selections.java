package pl.ais.commons.query;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Defines commonly used set of {@link Selection selections}.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class Selections {

    @SuppressWarnings("rawtypes")
    private static final Selection ALL_RECORDS = new UnsortableSelection<>(0, -1);

    /**
     * Constructs new instance.
     */
    private Selections() {
        super();
    }

    /**
     * Returns shared, unsortable {@link Selection} instance selecting all records.
     *
     * @return shared, unsortable {@link Selection} instance selecting all records
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <R extends Serializable> Selection<R> allRecords() {
        return ALL_RECORDS;
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
        return factory.createSelection(0, -1, copyOrderings(factory, orderings));
    }

    @SuppressWarnings("unchecked")
    private static <R, S extends Selection<R>> R[] copyOrderings(@Nonnull final SelectionFactory<R, S> factory,
                                                                 final R... orderings) {
        final Class<R[]> arrayType = (Class<R[]>) Array.newInstance(factory.getOrderingType(), 0).getClass();
        return Arrays.copyOf(orderings, orderings.length, arrayType);
    }

    /**
     * Creates and returns unsortable {@link Selection} instance selecting specified records.
     *
     * @param startIndex    the index of first record
     * @param displayLength the display length ({@code -1} when all records should be selected)
     * @return newly created unsortable {@link Selection} instance
     */
    @Nonnull
    public static <R extends Serializable> Selection<R> slice(
        @Nonnegative final int startIndex, final int displayLength) {
        return new UnsortableSelection<>(startIndex, displayLength);
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
        return factory.createSelection(startIndex, displayLength, copyOrderings(factory, orderings));
    }

}

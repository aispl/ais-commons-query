package pl.ais.commons.query;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.Serializable;
import java.util.Collection;

/**
 * Unsortable {@link Selection}.
 *
 * @param <R> the type of ordering
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@Immutable
public final class UnsortableSelection<R extends Serializable> extends AbstractSelection<R> {

    /**
     * Constructs new instance.
     *
     * @param startIndex the index of first record
     * @param displayLength the number of records (if {@code -1}, all records will be used)
     */
    UnsortableSelection(@Nonnegative final int startIndex, final int displayLength) {
        super(startIndex, displayLength);
    }

    @Override
    public Selection<R> withOrderings(@Nonnull final Collection<R> orderings) {
        throw new UnsupportedOperationException();
    }

}

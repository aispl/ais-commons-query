package pl.ais.commons.query;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

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
     * Identifies the original class version for which it is capable of writing streams and from which it can read.
     *
     * @see <a href="http://docs.oracle.com/javase/7/docs/platform/serialization/spec/version.html#6678">Type Changes Affecting Serialization</a>
     */
    private static final long serialVersionUID = 4418418601457537268L;

    /**
     * Constructs new instance.
     *
     * @param startIndex the index of first record
     * @param displayLength the number of records (if {@code -1}, all records will be used)
     */
    UnsortableSelection(@Nonnegative final int startIndex, final int displayLength) {
        super(startIndex, displayLength, Collections.<R> emptyList());
    }

    /**
     * Throws {@link UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException always, because ordering cannot be defined for this type of {@link Selection}.
     */
    @Override
    public Selection<R> withOrderings(@Nonnull final List<? extends R> orderings) {
        throw new UnsupportedOperationException();
    }

}

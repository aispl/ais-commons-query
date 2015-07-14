package pl.ais.commons.query;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class to be extended by all {@link Selection selections}.
 *
 * @param <R> the type of ordering
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@ThreadSafe
public abstract class AbstractSelection<R> implements Selection<R>, Serializable {

    private final int displayLength;

    private final R[] orderings;

    private final int startIndex;

    /**
     * Constructs new instance with default ordering applier.
     *
     * @param startIndex    the index of first record
     * @param displayLength the number of records (if {@code -1}, all records will be used)
     */
    protected AbstractSelection(@Nonnegative final int startIndex, final int displayLength,
                                @Nonnull final R... orderings) {

        // Verify constructor requirements, ...
        if (startIndex < 0) {
            throw new IllegalArgumentException("Start index should be non-negative.");
        }

        // ... and initialize this instance fields.
        this.startIndex = startIndex;
        this.displayLength = displayLength;
        this.orderings = Arrays.copyOf(orderings, orderings.length);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object) {
        boolean result = (this == object);
        if (!result && (null != object) && (getClass() == object.getClass())) {
            final AbstractSelection<?> other = (AbstractSelection<?>) object;
            result = (startIndex == other.startIndex) && (displayLength == other.displayLength)
                && Arrays.equals(orderings, other.orderings);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDisplayLength() {
        return displayLength;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public R[] getOrderings() {
        return orderings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    @SuppressWarnings("boxing")
    public int hashCode() {
        return Objects.hash(startIndex, displayLength, Arrays.hashCode(orderings));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSelectingSubset() {
        return (displayLength > 0);
    }

}

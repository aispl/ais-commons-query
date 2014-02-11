package pl.ais.commons.query;

import static com.google.common.base.Objects.toStringHelper;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import com.google.common.collect.ImmutableList;

/**
 * Class to be extended by all {@link Selection selections}.
 *
 * @param <R> the type of ordering
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@ThreadSafe
public abstract class AbstractSelection<R extends Serializable> implements Selection<R> {

    /**
     * Identifies the original class version for which it is capable of writing streams and from which it can read.
     *
     * @see <a href="http://docs.oracle.com/javase/7/docs/platform/serialization/spec/version.html#6678">Type Changes Affecting Serialization</a>
     */
    private static final long serialVersionUID = -5213498162004706554L;

    private final int displayLength;

    private final List<? extends R> orderings;

    private final int startIndex;

    /**
     * Constructs new instance with default ordering applier.
     *
     * @param startIndex the index of first record
     * @param displayLength the number of records (if {@code -1}, all records will be used)
     */
    protected AbstractSelection(@Nonnegative final int startIndex, final int displayLength,
        @Nonnull final List<? extends R> orderings) {

        // Verify constructor requirements, ...
        if (startIndex < 0) {
            throw new IllegalArgumentException("Start index should be non-negative.");
        }

        // ... and initialize this instance fields.
        this.startIndex = startIndex;
        this.displayLength = displayLength;
        this.orderings = ImmutableList.copyOf(orderings);
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
                && orderings.equals(other.orderings);
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
    public List<? extends R> getOrderings() {
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
        return Objects.hash(startIndex, displayLength, orderings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSelectingSubset() {
        return (displayLength > 0);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return toStringHelper(this).add("startIndex", startIndex)
            .add("displayLength", displayLength).add("orderings", orderings).toString();
    }

}

package pl.ais.commons.query;

import static com.google.common.base.Objects.toStringHelper;

import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Class to be extended by all {@linkplain Selection selections}.
 *
 * @param <R> the type of ordering element
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
    private static final long serialVersionUID = 4943985575820732558L;

    private final int displayLength;

    private final R[] orderings;

    private final int startIndex;

    /**
     * Constructs new instance with default ordering applier.
     *
     * @param startIndex the index of first record
     * @param displayLength the number of records (if {@code -1}, all records will be used)
     */
    @SafeVarargs
    protected AbstractSelection(final int startIndex, final int displayLength, final R... orderings) {

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
     * @return the orderings
     */
    @Override
    public R[] getOrderings() {
        return Arrays.copyOf(orderings, orderings.length);
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
    public int hashCode() {
        return (31 * ((31 * (31 + startIndex)) + displayLength)) + Arrays.hashCode(orderings);
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

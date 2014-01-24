package pl.ais.commons.query;

import javax.annotation.concurrent.ThreadSafe;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

/**
 * Class to be extended by all {@linkplain Selection selections}.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@ThreadSafe
public abstract class AbstractSelection implements Selection {

    /**
     * Identifies the original class version for which it is capable of writing streams and from which it can read.
     *
     * @see <a href="http://docs.oracle.com/javase/7/docs/platform/serialization/spec/version.html#6678">Type Changes Affecting Serialization</a>
     */
    private static final long serialVersionUID = 2533393473585095184L;

    private final int displayLength;

    private final int startIndex;

    /**
     * Constructs new instance with default ordering applier.
     *
     * @param startIndex the index of first record
     * @param displayLength the number of records (if {@code -1}, all records will be used)
     */
    protected AbstractSelection(final int startIndex, final int displayLength) {

        // Verify constructor requirements, ...
        if (startIndex < 0) {
            throw new IllegalArgumentException("Start index should be non-negative.");
        }

        // ... and initialize this instance fields.
        this.startIndex = startIndex;
        this.displayLength = displayLength;
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
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSelectingSubset() {
        return (displayLength > 0);
    }

    /**
     * @return new instance of {@link ToStringHelper} for this object, with appended start index and display length
     */
    protected ToStringHelper toStringHelper() {
        return Objects.toStringHelper(this).add("startIndex", startIndex).add("displayLength", displayLength);
    }

}

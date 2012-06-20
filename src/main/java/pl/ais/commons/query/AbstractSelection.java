package pl.ais.commons.query;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Base class for {@link Selection selections}.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.MissingSerialVersionUID"})
public abstract class AbstractSelection implements Selection {

    private final int displayLength;

    private final int startIndex;

    /**
     * Constructs new instance with default ordering applier.
     *
     * @param startIndex the index of first record
     * @param displayLength the number of records (if {@code -1}, all records will be used)
     */
    protected AbstractSelection(final int startIndex, final int displayLength) {
        Validate.isTrue(startIndex >= 0, "Start index shouldn't be negative.");
        this.startIndex = startIndex;
        this.displayLength = displayLength;
    }

    /**
     * @see pl.ais.commons.query.Selection#getDisplayLength()
     */
    @Override
    public int getDisplayLength() {
        return displayLength;
    }

    /**
     * @see pl.ais.commons.query.Selection#getStartIndex()
     */
    @Override
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * @see pl.ais.commons.query.Selection#isSelectingSubset()
     */
    @Override
    public boolean isSelectingSubset() {
        return (displayLength > 0);
    }

    /**
     * @return new instance of {@link ToStringBuilder} for this object, with appended start index and display length
     */
    protected ToStringBuilder toStringBuilder() {
        return new ToStringBuilder(this).append("startIndex", startIndex).append("displayLength", displayLength);
    }

}

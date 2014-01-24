package pl.ais.commons.query;

import static com.google.common.base.Objects.toStringHelper;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.google.common.collect.ImmutableList;

/**
 * Container for search results of specified type.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 * @param <T> determines the type of each search result
 */
@Immutable
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public final class SearchResults<T extends Serializable> implements Serializable {

    @SuppressWarnings("rawtypes")
    private static final SearchResults EMPTY = new SearchResults();

    /**
     * Identifies the original class version for which it is capable of writing streams and from which it can read.
     *
     * @see <a href="http://docs.oracle.com/javase/7/docs/platform/serialization/spec/version.html#6678">Type Changes Affecting Serialization</a>
     */
    private static final long serialVersionUID = 7272352995253612865L;

    /**
     * @return shared instance of empty search results
     */
    public static <E extends Serializable> SearchResults<E> emptySearchResults() {
        return EMPTY;
    }

    private final ImmutableList<T> elements;

    private final long totalRecords;

    /**
     * Constructs the instance with empty list of elements.
     */
    private SearchResults() {
        this(Collections.<T> emptyList(), 0L);
    }

    /**
     * Constructs new instance.
     *
     * @param elements elements to be included in this search results
     * @param totalRecords total number of records
     */
    public SearchResults(@Nonnull final List<T> elements, @Nonnegative final long totalRecords) {

        // Verify constructor requirements, ...
        if (null == elements) {
            throw new IllegalArgumentException("Elements are required.");
        }
        if (totalRecords < 0) {
            throw new IllegalArgumentException("Total number of records should be non-negative.");
        }

        // ... and initialize this instance fields.
        this.elements = ImmutableList.copyOf(elements);
        this.totalRecords = totalRecords;
    }

    /**
     * @return the unmodifiable view of elements
     */
    @Nonnull
    public List<T> getElements() {
        return elements;
    }

    /**
     * @return the total number of records
     */
    @Nonnegative
    public long getTotalRecords() {
        return totalRecords;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return toStringHelper(this).add("totalRecords", totalRecords).add("elements", elements).toString();
    }

}

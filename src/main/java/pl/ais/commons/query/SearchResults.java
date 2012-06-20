package pl.ais.commons.query;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Container for search results of specified type.
 *
 * @author Warlock, AIS.PL
 * @since 1.0
 * @param <T> determines the type of each search result
 */
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.MissingSerialVersionUID"})
public final class SearchResults<T extends Serializable> implements Serializable {

    @SuppressWarnings("rawtypes")
    private static final SearchResults EMPTY = new SearchResults();

    /**
     * @return empty search results
     */
    public static final <E extends Serializable> SearchResults<E> emptySearchResults() {
        return EMPTY;
    }

    private final List<T> elements;

    private final long totalRecords;

    /**
     * Constructs the instance with empty list of elements.
     */
    private SearchResults() {
        this(Collections.<T> emptyList(), 0L);
    }

    /**
     * Constructor.
     *
     * @param elements
     * @param totalRecords
     */
    public SearchResults(@Nonnull final List<T> elements, @Nonnegative final long totalRecords) {
        this.elements = elements;
        this.totalRecords = totalRecords;
        // Sanity check ...
        Validate.isTrue(null != this.elements, "Elements shouldn't be null");
        Validate.isTrue(this.totalRecords >= 0, "Total number of records should be non-negative");
    }

    /**
     * @return the elements
     */
    @Nonnull
    public List<T> getElements() {
        return Collections.unmodifiableList(elements);
    }

    /**
     * @return the totalRecords
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
        return new ToStringBuilder(this).append("totalRecords", totalRecords).append("elements", elements).toString();
    }

}

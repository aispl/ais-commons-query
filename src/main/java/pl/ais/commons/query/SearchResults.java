package pl.ais.commons.query;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Objects.toStringHelper;

/**
 * Container for search results of specified type.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 * @param <E> the type of each search result
 */
@Immutable
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public final class SearchResults<E> implements Serializable {

    @SuppressWarnings("rawtypes")
    private static final SearchResults EMPTY = new SearchResults();

    private final ImmutableList<E> elements;

    private final long totalRecords;

    /**
     * Constructs the instance with empty list of elements.
     */
    private SearchResults() {
        this(0L, Collections.<E> emptyList());
    }

    /**
     * Constructs new instance.
     *
     * @param totalRecords total number of records
     * @param elements elements to be included in this search results
     * @throws NullPointerException if any of {@code elements} is {@code null}
     */
    private SearchResults(@Nonnegative final long totalRecords, @Nonnull final List<E> elements) {

        // Verify constructor requirements, ...
        if (totalRecords < 0) {
            throw new IllegalArgumentException("Total number of records should be non-negative.");
        }

        Objects.requireNonNull(elements, "Elements are required.");

        // ... and initialize this instance fields.
        this.elements = ImmutableList.copyOf(elements);
        this.totalRecords = totalRecords;
    }

    /**
     * @return shared instance of empty search results
     */
    public static <E> SearchResults<E> emptySearchResults() {
        return EMPTY;
    }

    public static <E> SearchResults<E> of (final long totalRecords, @Nonnull final List<E> elements) {
          return (totalRecords <= 0) ? EMPTY : new SearchResults<E>(totalRecords, elements);
    }

    /**
     * @return the unmodifiable view of elements
     */
    @Nonnull
    public List<E> getElements() {
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

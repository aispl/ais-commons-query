package pl.ais.commons.query;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Container for search results of specified type.
 *
 * @param <E> the type of each search result
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@Immutable
public final class SearchResults<E> implements Serializable {

    @SuppressWarnings("rawtypes")
    private static final SearchResults EMPTY = new SearchResults();

    private static final long serialVersionUID = 6728443755768140782L;

    private final List<E> elements;

    private final long totalRecords;

    /**
     * Constructs the instance with empty list of elements.
     */
    private SearchResults() {
        this(0L, Collections.<E>emptyList());
    }

    /**
     * Constructs new instance.
     *
     * @param totalRecords total number of records
     * @param elements     elements to be included in this search results
     * @throws NullPointerException if any of {@code elements} is {@code null}
     */
    private SearchResults(@Nonnegative final long totalRecords, @Nonnull final List<E> elements) {

        // Verify constructor requirements, ...
        if (totalRecords < 0) {
            throw new IllegalArgumentException("Total number of records should be non-negative.");
        }

        Objects.requireNonNull(elements, "Elements are required.");

        // ... and initialize this instance fields.
        this.elements = Collections.unmodifiableList(elements);
        this.totalRecords = totalRecords;
    }

    /**
     * @return shared instance of empty search results
     */
    @SuppressWarnings("unchecked")
    public static <E> SearchResults<E> emptySearchResults() {
        return EMPTY;
    }

    @SuppressWarnings("unchecked")
    public static <E> SearchResults<E> of(final long totalRecords, @Nonnull final List<E> elements) {
        return (totalRecords <= 0) ? EMPTY : new SearchResults<>(totalRecords, elements);
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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("Total records: %d, fetched: %s", totalRecords, elements);
    }

}

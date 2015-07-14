package pl.ais.commons.query.dsl;

import com.querydsl.core.FetchableQuery;
import com.querydsl.core.ResultTransformer;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import pl.ais.commons.query.Selection;
import pl.ais.commons.query.Selections;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;

/**
 * Utility class for manipulating query results.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@Immutable
public final class Results {

    private final FetchableQuery<?, ?> query;

    private final Selection<OrderSpecifier> selection;

    private Results(@Nonnull final FetchableQuery<?, ?> query, @Nonnull final Selection<OrderSpecifier> selection) {
        super();

        // Verify constructor requirements, ...
        Objects.requireNonNull(query, "Query is required.");
        Objects.requireNonNull(selection, "Selection is required.");

        // ... and initialize this instance fields.
        this.query = query;
        this.selection = selection;
    }

    /**
     * Returns new {@link Results} instance for given query.
     *
     * @param query query determining the results
     * @return newly created {@link Results} instance for given query
     */
    public static Results forQuery(@Nonnull final FetchableQuery<?, ?> query) {
        return new Results(query, Selections.allRecords(QuerydslSelectionFactory.getInstance()));
    }

    /**
     * Executes encapsulated query and transforms the results using given transformer.
     *
     * @param transformer results transformer to be used
     * @return transformed query results
     */
    @SuppressWarnings("PMD.ShortMethodName")
    public <R> R as(final ResultTransformer<R> transformer) {
        FetchableQuery<?, ?> effective = query;
        if (selection.isSelectingSubset()) {
            effective = effective.offset(selection.getStartIndex()).limit(selection.getDisplayLength());
        }
        effective = effective.orderBy(selection.getOrderings());
        return transformer.transform(effective);
    }

    /**
     * Narrows the results to those matching given predicate.
     *
     * @param predicate predicate which should be matched by desired results
     * @return the results
     */
    public Results matching(@Nullable final Predicate predicate) {
        return (null == predicate) ? this : new Results(query.where(predicate), selection);
    }

    /**
     * Narrows the results using specified selection.
     *
     * @param selection determines which of the results will be fetched, and how they will be ordered
     * @return query determining the narrowed results
     */
    public Results within(@Nullable final Selection<OrderSpecifier> selection) {
        return (null == selection) ? this : new Results(query, selection);
    }

}

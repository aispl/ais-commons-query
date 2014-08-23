package pl.ais.commons.query.dsl;

import com.mysema.query.types.OrderSpecifier;
import pl.ais.commons.query.AbstractSelection;
import pl.ais.commons.query.Selection;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link Selection} implementation suitable for usage with QueryDSL.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@Immutable
public final class QuerydslSelection extends AbstractSelection<OrderSpecifier<?>> {

    /**
     * Identifies the original class version for which it is capable of writing streams and from which it can read.
     *
     * @see <a href="http://docs.oracle.com/javase/7/docs/platform/serialization/spec/version.html#6678">Type Changes
     * Affecting Serialization</a>
     */
    private static final long serialVersionUID = -3375748194910956154L;

    /**
     * @param startIndex    the index of first record
     * @param displayLength the number of records (if {@code -1}, all records will be used)
     * @param orderings     orderings which should be used
     */
    QuerydslSelection(@Nonnegative final int startIndex, final int displayLength,
                      final List<? extends OrderSpecifier<?>> orderings) {
        super(startIndex, displayLength, orderings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuerydslSelection withOrderings(@Nonnull final List<? extends OrderSpecifier<?>> orderings) {
        final List<OrderSpecifier<?>> modified = new ArrayList<>();
        // Copy those orderings from the current settings, which are not redefined by the method parameters ...
        processing:
        for (final OrderSpecifier<?> ordering : getOrderings()) {
            if (orderings.contains(ordering)) {
                continue processing;
            }
            modified.add(ordering);
        }
        modified.addAll(orderings);
        return new QuerydslSelection(getStartIndex(), getDisplayLength(), modified);
    }
}

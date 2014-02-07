package pl.ais.commons.query.dsl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import pl.ais.commons.query.AbstractSelection;
import pl.ais.commons.query.Selection;

import com.mysema.query.types.OrderSpecifier;

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
     * @see <a href="http://docs.oracle.com/javase/7/docs/platform/serialization/spec/version.html#6678">Type Changes Affecting Serialization</a>
     */
    private static final long serialVersionUID = -5422288218291549413L;

    /**
     * @param startIndex the index of first record
     * @param displayLength the number of records (if {@code -1}, all records will be used)
     * @param orderings orderings which should be used
     */
    public QuerydslSelection(final int startIndex, final int displayLength, final OrderSpecifier<?>... orderings) {
        super(startIndex, displayLength, orderings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuerydslSelection withOrderings(final OrderSpecifier<?>... orderings) {
        final List<OrderSpecifier<?>> modified = new ArrayList<>();
        // Copy those orderings from the current settings, which are not redefined by the method parameters ...
        processing: for (final OrderSpecifier<?> ordering : getOrderings()) {
            for (int i = 0; i < orderings.length; i++) {
                if (ordering.getTarget().equals(((OrderSpecifier<?>) orderings[i]).getTarget())) {
                    continue processing;
                }
            }
            modified.add(ordering);
        }
        modified.addAll(Arrays.asList(orderings));
        return new QuerydslSelection(getStartIndex(), getDisplayLength(),
            modified.toArray(new OrderSpecifier<?>[modified.size()]));
    }
}

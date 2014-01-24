package pl.ais.commons.query.dsl;

import java.io.Serializable;
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
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public final class QueryDSLSelection extends AbstractSelection {

    /**
     * Identifies the original class version for which it is capable of writing streams and from which it can read.
     *
     * @see <a href="http://docs.oracle.com/javase/7/docs/platform/serialization/spec/version.html#6678">Type Changes Affecting Serialization</a>
     */
    private static final long serialVersionUID = -46788264053284666L;

    private final OrderSpecifier<?>[] orderings;

    /**
     * @param startIndex the index of first record
     * @param displayLength the number of records (if {@code -1}, all records will be used)
     * @param orderings orderings which should be used
     */
    public QueryDSLSelection(final int startIndex, final int displayLength, final OrderSpecifier<?>... orderings) {
        super(startIndex, displayLength);
        this.orderings = Arrays.copyOf(orderings, orderings.length);
    }

    /**
     * @return the orderings
     */
    @Override
    @SuppressWarnings("unchecked")
    public OrderSpecifier<?>[] getOrderings() {
        return Arrays.copyOf(orderings, orderings.length);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return toStringHelper().add("orderings", orderings).toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <R extends Serializable> Selection withOrderings(@SuppressWarnings("hiding") final R... orderings) {
        final List<OrderSpecifier<?>> modified = new ArrayList<OrderSpecifier<?>>();
        // Copy those orderings from the current settings, which are not redefined by the method parameters ...
        processing: for (final OrderSpecifier<?> ordering : this.orderings) {
            for (int i = 0; i < orderings.length; i++) {
                if (ordering.getTarget().equals(((OrderSpecifier<?>) orderings[i]).getTarget())) {
                    continue processing;
                }
            }
            modified.add(ordering);
        }
        modified.addAll(Arrays.asList((OrderSpecifier<?>[]) orderings));
        return new QueryDSLSelection(getStartIndex(), getDisplayLength(),
            modified.toArray(new OrderSpecifier<?>[modified.size()]));
    }

}

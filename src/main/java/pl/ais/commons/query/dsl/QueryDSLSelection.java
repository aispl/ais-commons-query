package pl.ais.commons.query.dsl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.ais.commons.query.AbstractSelection;
import pl.ais.commons.query.Selection;

import com.mysema.query.types.OrderSpecifier;

/**
 * {@link Selection} implementation suitable for usage with QueryDSL.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public final class QueryDSLSelection extends AbstractSelection {

    private final OrderSpecifier<?>[] orderings;

    /**
     * @param startIndex the index of first record
     * @param displayLength the number of records (if {@code -1}, all records will be used)
     * @param orderings orderings which should be used
     */
    public QueryDSLSelection(final int startIndex, final int displayLength, final OrderSpecifier<?>... orderings) {
        super(startIndex, displayLength);
        this.orderings = orderings;
    }

    /**
     * @return the orderings
     */
    @Override
    @SuppressWarnings("unchecked")
    public OrderSpecifier<?>[] getOrderings() {
        return orderings;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return toStringBuilder().append("orderings", orderings).build();
    }

    /**
     * @see pl.ais.commons.query.Selection#withOrderings(R[])
     */
    @Override
    public <R extends Serializable> Selection withOrderings(@SuppressWarnings("hiding") final R... orderings) {
        final List<OrderSpecifier<?>> modified = new ArrayList<OrderSpecifier<?>>();
        // Copy those orderings from the current settings, which are not redefined by the method parameters ...
        processing: for (OrderSpecifier<?> ordering : this.orderings) {
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

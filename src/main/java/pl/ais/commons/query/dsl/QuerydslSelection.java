package pl.ais.commons.query.dsl;

import com.querydsl.core.types.OrderSpecifier;
import pl.ais.commons.query.AbstractSelection;
import pl.ais.commons.query.Selection;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * {@link Selection} implementation suitable for usage with QueryDSL.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@Immutable
public final class QuerydslSelection extends AbstractSelection<OrderSpecifier> {

    /**
     * @param startIndex    the index of first record
     * @param displayLength the number of records (if {@code -1}, all records will be used)
     * @param orderings     orderings which should be used
     */
    QuerydslSelection(@Nonnegative final int startIndex, final int displayLength,
                      final OrderSpecifier... orderings) {
        super(startIndex, displayLength, orderings);
    }


    @Override
    public Selection<OrderSpecifier> withOrderings(@Nonnull final Collection<OrderSpecifier> orderings) {
        final List<OrderSpecifier> modified = Arrays.stream(getOrderings())
                                                    .filter(ordering -> !orderings.contains(ordering))
                                                    .collect(toList());
        modified.addAll(orderings);
        return new QuerydslSelection(getStartIndex(), getDisplayLength(), modified.toArray(new OrderSpecifier[modified.size()]));
    }

}

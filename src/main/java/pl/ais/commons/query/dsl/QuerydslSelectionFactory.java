package pl.ais.commons.query.dsl;

import javax.annotation.concurrent.Immutable;

import pl.ais.commons.query.SelectionFactory;

import com.mysema.query.types.OrderSpecifier;

/**
 * {@link SelectionFactory} implementation creating selections suitable for usage with QueryDSL.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@Immutable
public class QuerydslSelectionFactory implements SelectionFactory<OrderSpecifier<?>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public QuerydslSelection createSelection(
        final int startIndex, final int displayLength, final OrderSpecifier<?>... orderings) {
        return new QuerydslSelection(startIndex, displayLength, orderings);
    }

}
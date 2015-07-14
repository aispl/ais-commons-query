package pl.ais.commons.query.dsl;

import com.querydsl.core.types.OrderSpecifier;
import pl.ais.commons.query.SelectionFactory;

import javax.annotation.concurrent.Immutable;

/**
 * {@link SelectionFactory} implementation creating selections suitable for usage with Querydsl.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@Immutable
public final class QuerydslSelectionFactory implements SelectionFactory<OrderSpecifier, QuerydslSelection> {

    private static final QuerydslSelectionFactory INSTANCE = new QuerydslSelectionFactory();

    /**
     * Constructs new instance.
     */
    private QuerydslSelectionFactory() {
        super();
    }

    /**
     * @return shared (singleton) instance of this factory
     */
    public static QuerydslSelectionFactory getInstance() {
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuerydslSelection createSelection(
        final int startIndex, final int displayLength, final OrderSpecifier... orderings) {
        return new QuerydslSelection(startIndex, displayLength, orderings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends OrderSpecifier> getOrderingType() {
        return OrderSpecifier.class;
    }

}

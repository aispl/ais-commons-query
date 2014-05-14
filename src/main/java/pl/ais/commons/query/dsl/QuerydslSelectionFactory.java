package pl.ais.commons.query.dsl;

import java.util.Arrays;

import javax.annotation.concurrent.Immutable;

import pl.ais.commons.query.SelectionFactory;

import com.mysema.query.types.OrderSpecifier;

/**
 * {@link SelectionFactory} implementation creating selections suitable for usage with Querydsl.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@Immutable
public final class QuerydslSelectionFactory implements SelectionFactory<OrderSpecifier<?>, QuerydslSelection> {

    private static final QuerydslSelectionFactory INSTANCE = new QuerydslSelectionFactory();

    /**
     * @return shared (singleton) instance of this factory
     */
    public static QuerydslSelectionFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Constructs new instance.
     */
    private QuerydslSelectionFactory() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuerydslSelection createSelection(
        final int startIndex, final int displayLength, final OrderSpecifier<?>... orderings) {
        return new QuerydslSelection(startIndex, displayLength, Arrays.asList(orderings));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getOrderingType() {
        return OrderSpecifier.class;
    }

}

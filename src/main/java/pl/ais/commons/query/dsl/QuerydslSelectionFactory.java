package pl.ais.commons.query.dsl;

import com.mysema.query.types.OrderSpecifier;
import pl.ais.commons.query.SelectionFactory;

import javax.annotation.concurrent.Immutable;
import java.util.Arrays;

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

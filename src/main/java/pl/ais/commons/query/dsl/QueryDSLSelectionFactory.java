package pl.ais.commons.query.dsl;

import java.io.Serializable;
import java.util.Arrays;

import pl.ais.commons.query.Selection;
import pl.ais.commons.query.SelectionFactory;

import com.mysema.query.types.OrderSpecifier;

/**
 * {@link SelectionFactory} implementation creating selections suitable for usage with QueryDSL.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public class QueryDSLSelectionFactory implements SelectionFactory {

    /**
     * @see pl.ais.commons.query.SelectionFactory#createSelection(int, int, R[])
     */
    @Override
    public <R extends Serializable> Selection createSelection(
        final int startIndex, final int displayLength, final R... orderings) {
        return new QueryDSLSelection(startIndex, displayLength, Arrays.copyOf(orderings, orderings.length,
            OrderSpecifier[].class));
    }

}

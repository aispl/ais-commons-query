package pl.ais.commons.query.dsl;

import com.querydsl.core.alias.Alias;
import com.querydsl.core.types.OrderSpecifier;
import org.junit.Assert;
import org.junit.Test;
import pl.ais.commons.query.dsl.internal.Person;

import static com.querydsl.core.alias.Alias.$;

/**
 * Verifies {@link QuerydslSelectionFactory} expectations.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
public class QuerydslSelectionFactoryExpectations {

    /**
     * Verifies if selections created by the factory are preserving the ordering used during the creation process.
     */
    @Test
    public void shouldPreserveOrdering() {

        // Given Query DSL selection factory.
        final QuerydslSelectionFactory selectionFactory = QuerydslSelectionFactory.getInstance();

        // When we create the selection using specified ordering
        final Person person = Alias.alias(Person.class);
        final OrderSpecifier<String> orderingByName = $(person.getName()).asc();
        final QuerydslSelection selection = selectionFactory.createSelection(0, 1, orderingByName);

        // Then the selection should preserve the ordering used during its creation
        final OrderSpecifier[] orderings = selection.getOrderings();
        Assert.assertTrue("Selection should preserve the ordering passed to factory.",
            (1 == orderings.length) && orderingByName.equals(orderings[0]));
    }

}

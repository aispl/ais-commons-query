package pl.ais.commons.query.dsl;

import static org.junit.Assert.assertTrue;
import static org.springframework.util.SerializationUtils.deserialize;
import static org.springframework.util.SerializationUtils.serialize;

import java.io.Serializable;

import org.junit.Test;

import pl.ais.commons.query.Selections;

/**
 * Verifies {@link QuerydslSelection} expectations.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings("static-method")
public class QuerydslSelectionExpectations {

    /**
     * Verifies if {@link QuerydslSelection} is {@link Serializable}.
     */
    @Test
    public void queryDslSelectionShouldBeSerializable() {

        // Given QueryDSLSelection, ...
        final QuerydslSelectionFactory selectionFactory = QuerydslSelectionFactory.getInstance();
        final QuerydslSelection selection = Selections.slice(0, 1, selectionFactory);

        // ... when we serialize and deserialize QueryDSLSelection, ...
        final QuerydslSelection deserialized = (QuerydslSelection) deserialize(serialize(selection));

        // ... then both instances should be equal and have same hash code.
        assertTrue("Deserialized instance differs from initial one.", (selection.hashCode() == deserialized.hashCode())
            && selection.equals(deserialized));
    }
}

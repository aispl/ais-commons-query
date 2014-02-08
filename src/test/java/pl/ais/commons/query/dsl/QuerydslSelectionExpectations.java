package pl.ais.commons.query.dsl;

import static junit.framework.Assert.assertTrue;
import static org.springframework.util.SerializationUtils.deserialize;
import static org.springframework.util.SerializationUtils.serialize;

import java.io.Serializable;

import org.junit.Test;

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
        final QuerydslSelectionFactory selectionFactory = new QuerydslSelectionFactory();
        final QuerydslSelection selection = selectionFactory.createSelection(0, 1);

        // ... when we serialize and deserialize QueryDSLSelection, ...
        final QuerydslSelection deserialized = (QuerydslSelection) deserialize(serialize(selection));

        // ... then both instances should be equal and have same hash code.
        assertTrue("Deserialized instance differs from initial one.", (selection.hashCode() == deserialized.hashCode())
            && selection.equals(deserialized));
    }
}

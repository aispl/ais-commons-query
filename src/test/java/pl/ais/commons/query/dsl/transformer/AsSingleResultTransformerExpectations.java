package pl.ais.commons.query.dsl.transformer;

import static com.mysema.query.alias.Alias.$;
import static com.mysema.query.alias.Alias.getAny;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static pl.ais.commons.query.dsl.transformer.Transformers.singleResult;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.ais.commons.query.dsl.Results;
import pl.ais.commons.query.dsl.internal.Person;

import com.mysema.query.alias.Alias;
import com.mysema.query.collections.CollQuery;
import com.mysema.query.types.Predicate;

/**
 * Verifies {@code AsSingleResultTransformer} expectations.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings({"static-method", "PMD.TooManyStaticImports"})
public class AsSingleResultTransformerExpectations {

    private static List<Person> createPersonList() {
        return Arrays.asList(new Person("Uncle Bob", 65), new Person("Aunt Mary", 62),
            new Person("Brother Louie", 27), new Person("Mum", 52));
    }

    /**
     * Verifies if first element is returned for not empty result set.
     */
    @Test
    public void shouldReturnFirstElementForNotEmptyResultSet() {

        // Given collection of some elements, persons in our case, ...
        final List<Person> persons = createPersonList();

        // ... when we query for any person from the list, and request single result, ...
        final Person person = Alias.alias(Person.class);
        final CollQuery query = new CollQuery().from($(person), persons);

        final Person result = Results.forQuery(query)
            .as(singleResult(getAny(person)));

        // ... then we should get first element from the list.
        assertEquals("Returned element should be first one.", persons.get(0), result);
    }

    /**
     * Verifies if {@code null} is returned when there are no matching records.
     */
    @SuppressWarnings("boxing")
    @Test
    public void shouldReturnNullWhenThereAreNoMatchingRecords() {

        // Given collection of some elements, persons in our case, ...
        final List<Person> persons = createPersonList();

        // ... when we query for persons matching criteria impossible to match, ...
        final Person person = Alias.alias(Person.class);
        final CollQuery query = new CollQuery().from($(person), persons);
        final Predicate predicate = $(person.getAge()).goe(200);

        final Person result = Results.forQuery(query).matching(predicate)
            .as(singleResult(getAny(person)));

        // ... then we should get null as the result.
        assertNull("Null should be returned.", result);
    }

}

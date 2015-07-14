package pl.ais.commons.query.dsl;

import com.querydsl.collections.CollQuery;
import com.querydsl.core.FetchableQuery;
import com.querydsl.core.alias.Alias;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import pl.ais.commons.query.dsl.internal.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.querydsl.core.alias.Alias.$;
import static com.querydsl.core.alias.Alias.getAny;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static pl.ais.commons.query.dsl.Transformers.firstResult;
import static pl.ais.commons.query.dsl.Transformers.stream;

/**
 * Verifies {@link Transformers} expectations.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
@RunWith(Suite.class)
@SuiteClasses({TransformersExpectations.AsFirstResultTransformerExpectations.class, TransformersExpectations.AsStreamResultTransformerExpectations.class})
public class TransformersExpectations {

    protected static List<Person> createPersonList() {
        return Arrays.asList(new Person("Uncle Bob", 65), new Person("Aunt Mary", 62),
            new Person("Brother Louie", 27), new Person("Mum", 52));
    }

    /**
     * Verifies {@link Transformers#firstResult} expectations.
     */
    static class AsFirstResultTransformerExpectations {

        /**
         * Verifies if first element is returned for not empty result set.
         */
        @Test
        public void shouldReturnFirstElementForNotEmptyResultSet() {

            // Given collection of some elements, persons in our case, ...
            final List<Person> persons = createPersonList();

            // ... when we query for any person from the list, and request single result, ...
            final Person person = Alias.alias(Person.class);
            final FetchableQuery query = new CollQuery().from($(person), persons);

            final Person result = Results.forQuery(query)
                                         .as(firstResult(getAny(person)));

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
            final FetchableQuery query = new CollQuery().from($(person), persons);
            final Predicate predicate = $(person.getAge()).goe(200);

            final Person result = Results.forQuery(query).matching(predicate)
                                         .as(firstResult(getAny(person)));

            // ... then we should get null as the result.
            assertNull("Null should be returned.", result);
        }

    }

    /**
     * Verifies {@link Transformers#stream(Expression)} expectations.
     *
     * @author Warlock, AIS.PL
     * @since 1.0
     */
    static class AsStreamResultTransformerExpectations {

        /**
         * Verifies if stream of all elements is provided by default.
         */
        @Test
        public void shouldProvideStreamOfAllResults() {

            // Given collection of some elements, persons in our case, ...
            final List<Person> persons = createPersonList();

            // ... when we query for any person from the list, and request the stream of results, ...
            final Person person = Alias.alias(Person.class);
            final FetchableQuery query = new CollQuery().from($(person), persons);

            final List<Person> result;
            try (final Stream<Person> personStream = Results.forQuery(query)
                                                            .as(stream(getAny(person)))) {
                result = personStream.collect(toList());
            }

            // ... then we should get first element from the list.
            assertEquals("Returned stream should contain all elements from the source list", persons, result);
        }

    }
}

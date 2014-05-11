package pl.ais.commons.query.dsl;

import static com.mysema.query.alias.Alias.$;
import static com.mysema.query.alias.Alias.getAny;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.ais.commons.query.dsl.transformer.Transformers.asList;
import static pl.ais.commons.query.dsl.transformer.Transformers.asNumberOfResults;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.ais.commons.query.Selections;
import pl.ais.commons.query.dsl.internal.Person;

import com.mysema.query.alias.Alias;
import com.mysema.query.collections.CollQuery;
import com.mysema.query.types.Predicate;

/**
 * Verifies {@link Results} expectations.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings({"boxing", "static-method", "PMD.TooManyStaticImports"})
public class ResultsExpectations {

    private static List<Person> createPersonList() {
        return Arrays.asList(new Person("Uncle Bob", 65), new Person("Aunt Mary", 62),
            new Person("Brother Louie", 27), new Person("Mum", 52));
    }

    private static QuerydslSelection createSelection(final int startIndex, final int displayLength) {
        return Selections.slice(startIndex, displayLength, new QuerydslSelectionFactory());
    }

    /**
     * Verifies if applying selection to the results affects number of results matching predicate.
     */
    @Test
    public void applyingSelectionShouldNotAffectNumberOfMatchingResults() {

        // Given collection of some elements, persons in our case, ...
        final List<Person> persons = createPersonList();

        // ... when we query for persons matching specified criteria, ...
        final Person person = Alias.alias(Person.class);
        final CollQuery query = new CollQuery().from($(person), persons);
        final Predicate predicate = $(person.getAge()).goe(60);
        final QuerydslSelection selection = createSelection(0, 1);

        final Long numberOfResults = Results.forQuery(query).matching(predicate).within(selection)
            .transform(asNumberOfResults());

        // ... then we should get exactly two results.
        assertEquals("There should be exactly 2 results matching the criteria.", Long.valueOf(2), numberOfResults);
    }

    /**
     * Verifies if applying selection to the results limits those, which are fetched.
     */
    @Test
    public void shouldLimitFetchedResultsWhenSelectionIsApplied() {

        // Given collection of some elements, persons in our case, ...
        final List<Person> persons = createPersonList();

        // ... when we query for names of persons matching specified criteria, ...
        final Person person = Alias.alias(Person.class);
        final CollQuery query = new CollQuery().from($(person), persons);
        final Predicate predicate = $(person.getAge()).goe(60);
        final QuerydslSelection selection = createSelection(0, 1);

        final List<String> results = Results.forQuery(query).matching(predicate).within(selection)
            .transform(asList($(person.getName())));

        // ... then we should get exactly one result, 'Uncle Bob'.
        assertTrue("", (1 == results.size()) && results.get(0).equals("Uncle Bob"));
    }

    /**
     * Verifies if results are limited when predicate is applied.
     */
    @Test
    public void shouldLimitResultsWhenPredicateIsApplied() {

        // Given collection of some elements, persons in our case, ...
        final List<Person> persons = createPersonList();

        // ... when we query for persons matching specified criteria, ...
        final Person person = Alias.alias(Person.class);
        final CollQuery query = new CollQuery().from($(person), persons);
        final Predicate predicate = $(person.getAge()).goe(60);

        final Long numberOfResults = Results.forQuery(query).matching(predicate).transform(asNumberOfResults());

        // ... then we should get exactly two results.
        assertEquals("There should be exactly 2 results matching the criteria.", Long.valueOf(2), numberOfResults);
    }

    /**
     * Verifies if all elements are returned when no limitations were applied.
     */
    @Test
    public void shouldReturnAllElementsWhenNoLimitationsApplied() {

        // Given collection of some elements, persons in our case, ...
        final List<Person> persons = createPersonList();

        // ... when we query for all persons from the list, ...
        final Person person = Alias.alias(Person.class);
        final CollQuery query = new CollQuery().from($(person), persons);

        final List<Person> results = Results.forQuery(query).transform(asList(getAny(person)));

        // ... then results should be equal to our persons list.
        assertEquals("Result should be equal to all persons list.", persons, results);
    }

}

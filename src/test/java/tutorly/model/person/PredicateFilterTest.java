package tutorly.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import tutorly.testutil.PersonBuilder;

public class PredicateFilterTest {
    private final NameContainsKeywordsPredicate firstPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
    private final PhoneContainsKeywordsPredicate secondPredicate =
            new PhoneContainsKeywordsPredicate(Arrays.asList("9123", "876"));

    @Test
    public void equals() {
        PredicateFilter firstFilter = new PredicateFilter(Collections.singletonList(firstPredicate));
        PredicateFilter secondFilter = new PredicateFilter(Collections.singletonList(secondPredicate));

        // same object -> returns true
        assertTrue(firstFilter.equals(firstFilter));

        // same predicates -> returns true
        PredicateFilter firstFilterCopy = new PredicateFilter(Collections.singletonList(firstPredicate));
        assertTrue(firstFilter.equals(firstFilterCopy));

        // different types -> returns false
        assertFalse(firstFilter.equals(1));

        // null -> returns false
        assertFalse(firstFilter.equals(null));

        // different predicates -> returns false
        assertFalse(firstFilter.equals(secondFilter));
    }

    @Test
    public void toStringMethod() {
        PredicateFilter filter = new PredicateFilter(Arrays.asList(firstPredicate, secondPredicate));
        String expectedString = Arrays.asList(firstPredicate, secondPredicate).toString();

        assertEquals(expectedString, filter.toString());
    }

    @Test
    public void getPredicate_multiplePredicatesPresent_returnsCombinedPredicate() {
        PredicateFilter filter = new PredicateFilter(Arrays.asList(firstPredicate, secondPredicate));
        Predicate<Person> combinedPredicate = filter.getPredicate();

        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").withPhone("91234567").build();
        Person charlie = new PersonBuilder().withName("Charlie").build();

        // Combined predicate should return true for Alice or Bob
        assertTrue(combinedPredicate.test(alice));
        assertTrue(combinedPredicate.test(bob));

        // Combined predicate should return false for Charlie
        assertFalse(combinedPredicate.test(charlie));
    }

    @Test
    public void getPredicate_emptyPredicateList_returnsAlwaysTruePredicate() {
        PredicateFilter filter = new PredicateFilter(Collections.emptyList());
        Predicate<Person> combinedPredicate = filter.getPredicate();

        Person person = new PersonBuilder().build();

        // Combined predicate should always return true for an empty predicate list
        assertTrue(combinedPredicate.test(person));
    }
}

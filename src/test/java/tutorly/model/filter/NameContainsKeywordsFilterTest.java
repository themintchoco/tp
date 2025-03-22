package tutorly.model.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import tutorly.model.AddressBook;
import tutorly.testutil.PersonBuilder;

public class NameContainsKeywordsFilterTest {

    private final AddressBook addressBook = getTypicalAddressBook();

    @Test
    public void equals() {
        List<String> firstFilterKeywordList = Collections.singletonList("first");
        List<String> secondFilterKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsFilter firstFilter = new NameContainsKeywordsFilter(firstFilterKeywordList);
        NameContainsKeywordsFilter secondFilter = new NameContainsKeywordsFilter(secondFilterKeywordList);

        // same object -> returns true
        assertTrue(firstFilter.equals(firstFilter));

        // same values -> returns true
        NameContainsKeywordsFilter firstFilterCopy = new NameContainsKeywordsFilter(firstFilterKeywordList);
        assertTrue(firstFilter.equals(firstFilterCopy));

        // different types -> returns false
        assertFalse(firstFilter.equals(1));

        // null -> returns false
        assertFalse(firstFilter.equals(null));

        // different person -> returns false
        assertFalse(firstFilter.equals(secondFilter));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsFilter filter = new NameContainsKeywordsFilter(Collections.singletonList("Alice"));
        assertTrue(filter.toPredicate(addressBook).test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        filter = new NameContainsKeywordsFilter(Arrays.asList("Alice", "Bob"));
        assertTrue(filter.toPredicate(addressBook).test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        filter = new NameContainsKeywordsFilter(Arrays.asList("Bob", "Carol"));
        assertTrue(filter.toPredicate(addressBook).test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        filter = new NameContainsKeywordsFilter(Arrays.asList("aLIce", "bOB"));
        assertTrue(filter.toPredicate(addressBook).test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsFilter filter = new NameContainsKeywordsFilter(Collections.emptyList());
        assertFalse(filter.toPredicate(addressBook).test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        filter = new NameContainsKeywordsFilter(Arrays.asList("Carol"));
        assertFalse(filter.toPredicate(addressBook).test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        filter = new NameContainsKeywordsFilter(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(filter.toPredicate(addressBook).test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameContainsKeywordsFilter filter = new NameContainsKeywordsFilter(keywords);

        String expected = NameContainsKeywordsFilter.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, filter.toString());
    }

}

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

public class PhoneContainsKeywordsFilterTest {

    private final AddressBook addressBook = getTypicalAddressBook();

    @Test
    public void equals() {
        List<String> firstFilterKeywordList = Collections.singletonList("9123");
        List<String> secondFilterKeywordList = Arrays.asList("9123", "876");

        PhoneContainsKeywordsFilter firstFilter = new PhoneContainsKeywordsFilter(firstFilterKeywordList);
        PhoneContainsKeywordsFilter secondFilter = new PhoneContainsKeywordsFilter(secondFilterKeywordList);

        // same object -> returns true
        assertTrue(firstFilter.equals(firstFilter));

        // same values -> returns true
        PhoneContainsKeywordsFilter firstFilterCopy = new PhoneContainsKeywordsFilter(firstFilterKeywordList);
        assertTrue(firstFilter.equals(firstFilterCopy));

        // different types -> returns false
        assertFalse(firstFilter.equals(1));

        // null -> returns false
        assertFalse(firstFilter.equals(null));

        // different values -> returns false
        assertFalse(firstFilter.equals(secondFilter));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        PhoneContainsKeywordsFilter filter = new PhoneContainsKeywordsFilter(Collections.singletonList("912"));
        assertTrue(filter.toPredicate(addressBook).test(new PersonBuilder().withPhone("91234567").build()));

        // Multiple keywords
        filter = new PhoneContainsKeywordsFilter(Arrays.asList("912", "456"));
        assertTrue(filter.toPredicate(addressBook).test(new PersonBuilder().withPhone("91234567").build()));

        // Only one matching keyword
        filter = new PhoneContainsKeywordsFilter(Arrays.asList("912", "000"));
        assertTrue(filter.toPredicate(addressBook).test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhoneContainsKeywordsFilter filter = new PhoneContainsKeywordsFilter(Collections.emptyList());
        assertFalse(filter.toPredicate(addressBook).test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        filter = new PhoneContainsKeywordsFilter(Collections.singletonList("000"));
        assertFalse(filter.toPredicate(addressBook).test(new PersonBuilder().withPhone("91234567").build()));

        // Keywords match name, email and address, but does not match phone
        filter = new PhoneContainsKeywordsFilter(Arrays.asList("000", "alice@email.com", "Main", "Alice"));
        assertFalse(filter.toPredicate(addressBook).test(new PersonBuilder().withName("Alice").withPhone("91234567")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PhoneContainsKeywordsFilter filter = new PhoneContainsKeywordsFilter(keywords);

        String expected = PhoneContainsKeywordsFilter.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, filter.toString());
    }

}

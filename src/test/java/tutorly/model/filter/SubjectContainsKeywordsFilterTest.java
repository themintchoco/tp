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
import tutorly.testutil.SessionBuilder;

public class SubjectContainsKeywordsFilterTest {
    private final AddressBook addressBook = getTypicalAddressBook();

    @Test
    public void equals() {
        List<String> firstFilterKeywordList = Collections.singletonList("first");
        List<String> secondFilterKeywordList = Arrays.asList("first", "second");

        SubjectContainsKeywordsFilter firstFilter = new SubjectContainsKeywordsFilter(firstFilterKeywordList);
        SubjectContainsKeywordsFilter secondFilter = new SubjectContainsKeywordsFilter(secondFilterKeywordList);

        // same object -> returns true
        assertTrue(firstFilter.equals(firstFilter));

        // same values -> returns true
        SubjectContainsKeywordsFilter firstFilterCopy = new SubjectContainsKeywordsFilter(firstFilterKeywordList);
        assertTrue(firstFilter.equals(firstFilterCopy));

        // different types -> returns false
        assertFalse(firstFilter.equals(1));

        // null -> returns false
        assertFalse(firstFilter.equals(null));

        // different keywords -> returns false
        assertFalse(firstFilter.equals(secondFilter));
    }

    @Test
    public void test_subjectContainsKeywords_returnsTrue() {
        // One keyword
        SubjectContainsKeywordsFilter filter = new SubjectContainsKeywordsFilter(Collections.singletonList("Math"));
        assertTrue(filter.toPredicate(addressBook).test(new SessionBuilder().withSubject("Mathematics").build()));

        // Multiple keywords
        filter = new SubjectContainsKeywordsFilter(Arrays.asList("Math", "Eng"));
        assertTrue(filter.toPredicate(addressBook).test(new SessionBuilder().withSubject("Mathematics Eng").build()));

        // Only one matching keyword
        assertTrue(filter.toPredicate(addressBook).test(new SessionBuilder().withSubject("Math Science").build()));

        // Mixed-case keyword
        filter = new SubjectContainsKeywordsFilter(Collections.singletonList("mAtH"));
        assertTrue(filter.toPredicate(addressBook).test(new SessionBuilder().withSubject("Math").build()));
    }

    @Test
    public void test_subjectDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SubjectContainsKeywordsFilter filter = new SubjectContainsKeywordsFilter(Collections.emptyList());
        assertFalse(filter.toPredicate(addressBook).test(new SessionBuilder().withSubject("Mathematics").build()));

        // Non-matching keyword
        filter = new SubjectContainsKeywordsFilter(Collections.singletonList("Eng"));
        assertFalse(filter.toPredicate(addressBook).test(new SessionBuilder().withSubject("Mathematics").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        SubjectContainsKeywordsFilter filter = new SubjectContainsKeywordsFilter(keywords);

        String expected = SubjectContainsKeywordsFilter.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, filter.toString());
    }

}

package tutorly.model.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import tutorly.model.AddressBook;
import tutorly.testutil.SessionBuilder;

public class DateSessionFilterTest {
    private final AddressBook addressBook = getTypicalAddressBook();
    private final LocalDate firstDate = LocalDate.of(2025, 1, 1);
    private final LocalDate secondDate = LocalDate.of(2025, 3, 25);

    @Test
    public void equals() {
        DateSessionFilter firstFilter = new DateSessionFilter(firstDate);
        DateSessionFilter secondFilter = new DateSessionFilter(secondDate);

        // same object -> returns true
        assertTrue(firstFilter.equals(firstFilter));

        // same values -> returns true
        DateSessionFilter firstFilterCopy = new DateSessionFilter(firstDate);
        assertTrue(firstFilter.equals(firstFilterCopy));

        // different types -> returns false
        assertFalse(firstFilter.equals(1));

        // null -> returns false
        assertFalse(firstFilter.equals(null));

        // different date -> returns false
        assertFalse(firstFilter.equals(secondFilter));
    }

    @Test
    public void test_sessionOnDate_returnsTrue() {
        DateSessionFilter filter = new DateSessionFilter(firstDate);
        assertTrue(filter.toPredicate(addressBook).test(new SessionBuilder().withDate(firstDate).build()));
    }

    @Test
    public void test_sessionNotOnDate_returnsFalse() {
        DateSessionFilter filter = new DateSessionFilter(firstDate);
        assertFalse(filter.toPredicate(addressBook).test(new SessionBuilder().withDate(secondDate).build()));
    }

    @Test
    public void toStringMethod() {
        DateSessionFilter filter = new DateSessionFilter(firstDate);

        String expected = DateSessionFilter.class.getCanonicalName() + "{date=" + firstDate + "}";
        assertEquals(expected, filter.toString());
    }

}

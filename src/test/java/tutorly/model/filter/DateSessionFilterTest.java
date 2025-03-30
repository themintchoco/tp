package tutorly.model.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import tutorly.model.AddressBook;

public class DateSessionFilterTest {
    private final AddressBook addressBook = getTypicalAddressBook();
    private final LocalDate firstDate = LocalDate.of(2025, 1, 1);
    private final LocalDate secondDate = LocalDate.of(2025, 3, 25);

    @Test
    public void equals() {
        DateSessionFilter firstFilter = new DateSessionFilter(firstDate);
        DateSessionFilter secondFilter = new DateSessionFilter(secondDate);

        // same object -> returns true
        assertEquals(firstFilter, firstFilter);

        // same values -> returns true
        DateSessionFilter firstFilterCopy = new DateSessionFilter(firstDate);
        assertEquals(firstFilter, firstFilterCopy);

        // different types -> returns false
        assertNotEquals(1, firstFilter);

        // null -> returns false
        assertNotEquals(null, firstFilter);

        // different date -> returns false
        assertNotEquals(firstFilter, secondFilter);
    }

    // TODO: Uncomment this test when date filter method works with timeslot implemented
    //    @Test
    //    public void test_sessionOnDate_returnsTrue() {
    //        DateSessionFilter filter = new DateSessionFilter(firstDate);
    //        assertTrue(filter.toPredicate(addressBook).test(new SessionBuilder().withTimeslot(firstDate).build()));
    //    }
    //
    //    @Test
    //    public void test_sessionNotOnDate_returnsFalse() {
    //        DateSessionFilter filter = new DateSessionFilter(firstDate);
    //        assertFalse(filter.toPredicate(addressBook).test(new SessionBuilder().withTimeslot(secondDate).build()));
    //    }

    @Test
    public void toStringMethod() {
        DateSessionFilter filter = new DateSessionFilter(firstDate);

        String expected = DateSessionFilter.class.getCanonicalName() + "{date=" + firstDate + "}";
        assertEquals(expected, filter.toString());
    }

}

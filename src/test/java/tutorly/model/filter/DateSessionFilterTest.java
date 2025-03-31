package tutorly.model.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import tutorly.model.AddressBook;
import tutorly.model.session.Session;
import tutorly.model.session.Timeslot;
import tutorly.testutil.SessionBuilder;

public class DateSessionFilterTest {
    private final AddressBook addressBook = getTypicalAddressBook();
    private final LocalDate firstDate = LocalDate.of(2025, 1, 1);
    private final LocalDate secondDate = LocalDate.of(2025, 1, 3);

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

    @Test
    public void test_sessionOnDate_returnsTrue() {
        // on start date
        DateSessionFilter filter = new DateSessionFilter(firstDate);
        Session session = new SessionBuilder().withTimeslot(
                new Timeslot(firstDate.atTime(0, 0), secondDate.atTime(0, 0))).build();
        assertTrue(filter.toPredicate(addressBook).test(session));

        // within date range
        filter = new DateSessionFilter(firstDate.plusDays(1));
        assertTrue(filter.toPredicate(addressBook).test(session));

        // on end date
        filter = new DateSessionFilter(secondDate);
        assertTrue(filter.toPredicate(addressBook).test(session));
    }

    @Test
    public void test_sessionNotOnDate_returnsFalse() {
        // before start date
        DateSessionFilter filter = new DateSessionFilter(firstDate.minusDays(1));
        Session session = new SessionBuilder().withTimeslot(
                new Timeslot(firstDate.atTime(0, 0), secondDate.atTime(0, 0))).build();
        assertFalse(filter.toPredicate(addressBook).test(session));

        // after end date
        filter = new DateSessionFilter(secondDate.plusDays(1));
        assertFalse(filter.toPredicate(addressBook).test(session));
    }

    @Test
    public void toStringMethod() {
        DateSessionFilter filter = new DateSessionFilter(firstDate);

        String expected = DateSessionFilter.class.getCanonicalName() + "{date=" + firstDate + "}";
        assertEquals(expected, filter.toString());
    }

}

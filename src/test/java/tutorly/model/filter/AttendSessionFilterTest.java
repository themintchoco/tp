package tutorly.model.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.testutil.TypicalAddressBook.ALICE;
import static tutorly.testutil.TypicalAddressBook.BENSON;
import static tutorly.testutil.TypicalAddressBook.CARL;
import static tutorly.testutil.TypicalAddressBook.ENGLISH_SESSION;
import static tutorly.testutil.TypicalAddressBook.MATH_SESSION;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tutorly.model.AddressBook;
import tutorly.model.person.Person;

public class AttendSessionFilterTest {

    private final AddressBook addressBook = getTypicalAddressBook();

    @Test
    public void equals() {
        int firstFilterSessionId = 1;
        int secondFilterSessionId = 2;

        AttendSessionFilter firstFilter = new AttendSessionFilter(firstFilterSessionId);
        AttendSessionFilter secondFilter = new AttendSessionFilter(secondFilterSessionId);

        // same object -> returns true
        assertTrue(firstFilter.equals(firstFilter));

        // same values -> returns true
        AttendSessionFilter firstFilterCopy = new AttendSessionFilter(firstFilterSessionId);
        assertTrue(firstFilter.equals(firstFilterCopy));

        // different types -> returns false
        assertFalse(firstFilter.equals(1));

        // null -> returns false
        assertFalse(firstFilter.equals(null));

        // different values -> returns false
        assertFalse(firstFilter.equals(secondFilter));
    }

    @Test
    public void test_attendSession_returnsTrue() {
        Filter<Person> filter = new AttendSessionFilter(MATH_SESSION.getId());
        assertTrue(filter.toPredicate(addressBook).test(ALICE));

        filter = new AttendSessionFilter(ENGLISH_SESSION.getId());
        assertTrue(filter.toPredicate(addressBook).test(CARL));
    }

    @Test
    public void test_attendSession_returnsFalse() {
        AttendSessionFilter filter = new AttendSessionFilter(MATH_SESSION.getId());
        assertFalse(filter.toPredicate(addressBook).test(CARL));

        filter = new AttendSessionFilter(ENGLISH_SESSION.getId());
        assertFalse(filter.toPredicate(addressBook).test(BENSON));
    }

    @Test
    public void toStringMethod() {
        int sessionId = MATH_SESSION.getId();
        AttendSessionFilter filter = new AttendSessionFilter(sessionId);

        String expected = AttendSessionFilter.class.getCanonicalName() + "{sessionId=" + sessionId + "}";
        assertEquals(expected, filter.toString());
    }

}

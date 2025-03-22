package tutorly.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.testutil.TypicalAddressBook.ALICE;
import static tutorly.testutil.TypicalAddressBook.ALICE_ATTEND_MATH;
import static tutorly.testutil.TypicalAddressBook.BENSON_ATTEND_MATH;
import static tutorly.testutil.TypicalAddressBook.CARL;
import static tutorly.testutil.TypicalAddressBook.CARL_ATTEND_ENGLISH;
import static tutorly.testutil.TypicalAddressBook.ENGLISH_SESSION;
import static tutorly.testutil.TypicalAddressBook.MATH_SESSION;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tutorly.model.attendancerecord.AttendanceRecord;

public class AttendSessionPredicateTest {
    private final ObservableList<AttendanceRecord> attendanceRecords =
            FXCollections.observableList(Arrays.asList(ALICE_ATTEND_MATH, CARL_ATTEND_ENGLISH));

    @Test
    public void equals() {
        int firstPredicateSessionId = 1;
        int secondPredicateSessionId = 2;

        AttendSessionPredicate firstPredicate = new AttendSessionPredicate(firstPredicateSessionId);
        AttendSessionPredicate secondPredicate = new AttendSessionPredicate(secondPredicateSessionId);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AttendSessionPredicate firstPredicateCopy =
                new AttendSessionPredicate(firstPredicateSessionId);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different values -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_attendSession_returnsTrue() {
        AttendSessionPredicate predicate = new AttendSessionPredicate(MATH_SESSION.getId());
        predicate.initFilteredAttendanceRecords(attendanceRecords);
        assertTrue(predicate.test(ALICE));

        predicate = new AttendSessionPredicate(ENGLISH_SESSION.getId());
        predicate.initFilteredAttendanceRecords(attendanceRecords);
        assertTrue(predicate.test(CARL));
    }

    @Test
    public void test_attendSession_returnsFalse() {
        AttendSessionPredicate predicate = new AttendSessionPredicate(MATH_SESSION.getId());
        predicate.initFilteredAttendanceRecords(attendanceRecords);
        assertFalse(predicate.test(CARL));

        predicate = new AttendSessionPredicate(ENGLISH_SESSION.getId());
        predicate.initFilteredAttendanceRecords(attendanceRecords);
        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void toStringMethod() {
        int sessionId = MATH_SESSION.getId();
        AttendSessionPredicate predicate = new AttendSessionPredicate(sessionId);
        ObservableList<AttendanceRecord> attendanceRecords =
                FXCollections.observableList(Arrays.asList(ALICE_ATTEND_MATH, BENSON_ATTEND_MATH));
        predicate.initFilteredAttendanceRecords(attendanceRecords);

        String expected = AttendSessionPredicate.class.getCanonicalName()
                + "{sessionId=" + sessionId
                + ", attendanceRecords=" + attendanceRecords + "}";
        assertEquals(expected, predicate.toString());
    }
}

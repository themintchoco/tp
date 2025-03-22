package tutorly.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import tutorly.commons.util.ToStringBuilder;
import tutorly.model.attendancerecord.AttendanceRecord;

/**
 * Tests that a {@code Person} is attending a session with the given session id.
 */
public class AttendSessionPredicate implements Predicate<Person> {
    private final int sessionId;
    private ObservableList<AttendanceRecord> filteredAttendanceRecords;

    public AttendSessionPredicate(int sessionId) {
        this.sessionId = sessionId;
    }

    public void initFilteredAttendanceRecords(ObservableList<AttendanceRecord> attendanceRecords) {
        requireNonNull(attendanceRecords);
        this.filteredAttendanceRecords = attendanceRecords.filtered(record -> record.getSessionId() == sessionId);
    }

    public static boolean isAttendSessionPredicate(Predicate<Person> predicate) {
        return predicate instanceof AttendSessionPredicate;
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(filteredAttendanceRecords);
        return filteredAttendanceRecords.stream().anyMatch(record -> record.getStudentId() == person.getId());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AttendSessionPredicate)) {
            return false;
        }

        AttendSessionPredicate otherAttendSessionPredicate = (AttendSessionPredicate) other;
        return sessionId == otherAttendSessionPredicate.sessionId
                && Objects.equals(filteredAttendanceRecords, otherAttendSessionPredicate.filteredAttendanceRecords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("sessionId", sessionId)
                .add("attendanceRecords", filteredAttendanceRecords)
                .toString();
    }
}

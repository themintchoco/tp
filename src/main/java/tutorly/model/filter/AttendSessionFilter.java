package tutorly.model.filter;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import tutorly.commons.util.ToStringBuilder;
import tutorly.model.ReadOnlyAddressBook;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.person.Person;

/**
 * Represents a filter for a {@code Person} who attends a session with the given session ID.
 */
public class AttendSessionFilter implements Filter<Person> {
    private final int sessionId;

    public AttendSessionFilter(int sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public Predicate<Person> toPredicate(ReadOnlyAddressBook addressBook) {
        requireNonNull(addressBook);

        List<AttendanceRecord> filteredAttendanceRecords =
                addressBook.getAttendanceRecordsList().filtered(record -> record.getSessionId() == sessionId);
        return person -> filteredAttendanceRecords.stream().anyMatch(record -> record.getStudentId() == person.getId());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AttendSessionFilter otherAttendSessionFilter)) {
            return false;
        }

        return sessionId == otherAttendSessionFilter.sessionId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("sessionId", sessionId).toString();
    }

}

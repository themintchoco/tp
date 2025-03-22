package tutorly.model;

import javafx.collections.ObservableList;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.person.Person;
import tutorly.model.session.Session;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the sessions list.
     */
    ObservableList<Session> getSessionList();

    /**
     * Returns an unmodifiable view of the attendance records list.
     */
    ObservableList<AttendanceRecord> getAttendanceRecordsList();
}

package tutorly.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static tutorly.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tutorly.testutil.Assert.assertThrows;
import static tutorly.testutil.TypicalAddressBook.ALICE;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import tutorly.commons.util.ObservableListUtil;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.attendancerecord.Feedback;
import tutorly.model.person.Person;
import tutorly.model.session.Session;
import tutorly.model.uniquelist.exceptions.DuplicateElementException;
import tutorly.testutil.PersonBuilder;
import tutorly.testutil.SessionBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicateElementException() {
        Person editedAlice = new PersonBuilder(ALICE)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons, List.of(), List.of());

        assertThrows(DuplicateElementException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateAttendanceRecords_throwsDuplicateElementException() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(1, 1,
                false, Feedback.empty());
        List<AttendanceRecord> newAttendanceRecords = Arrays.asList(attendanceRecord, attendanceRecord);
        AddressBookStub newData = new AddressBookStub(List.of(), List.of(), newAttendanceRecords);

        assertThrows(DuplicateElementException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void addSession_validSession_success() {
        Session session = new SessionBuilder().build();
        addressBook.addSession(session);
        assertTrue(addressBook.hasSession(session));
    }

    @Test
    public void hasSession_nullSession_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasSession(null));
    }

    @Test
    public void hasSession_sessionNotInAddressBook_returnsFalse() {
        Session session = new SessionBuilder().build();
        assertFalse(addressBook.hasSession(session));
    }

    @Test
    public void hasSession_sessionInAddressBook_returnsTrue() {
        Session session = new SessionBuilder().build();
        addressBook.addSession(session);
        assertTrue(addressBook.hasSession(session));
    }

    @Test
    public void removeSession_existingSession_success() {
        Session session = new SessionBuilder().build();
        addressBook.addSession(session);
        addressBook.removeSession(session);
        assertFalse(addressBook.hasSession(session));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook
                .getPersonList().remove(0));
    }

    @Test
    public void hasAttendanceRecord_nullAttendanceRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasAttendanceRecord(null));
    }

    @Test
    public void hasAttendanceRecord_attendanceRecordNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasAttendanceRecord(new AttendanceRecord(1, 1,
                false, Feedback.empty())));
    }

    @Test
    public void hasAttendanceRecord_attendanceRecordInAddressBook_returnsTrue() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(1, 1,
                false, Feedback.empty());
        addressBook.addAttendanceRecord(attendanceRecord);
        assertTrue(addressBook.hasAttendanceRecord(attendanceRecord));
    }

    @Test
    public void hasAttendanceRecord_equivalentAttendanceRecordInAddressBook_returnsTrue() {
        addressBook.addAttendanceRecord(new AttendanceRecord(1, 1, false, Feedback.empty()));
        assertTrue(addressBook.hasAttendanceRecord(new AttendanceRecord(1, 1,
                true, Feedback.empty())));
    }

    @Test
    public void getAttendanceRecordsList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getAttendanceRecordsList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName();

        expected += "{persons=" + addressBook.getPersonList();
        expected += ", sessions=" + addressBook.getSessionList();
        expected += ", attendanceRecords=" + addressBook.getAttendanceRecordsList();

        expected += "}";

        assertEquals(expected, addressBook.toString());
    }

    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = ObservableListUtil.arrayList();
        private final ObservableList<Session> sessions = ObservableListUtil.arrayList();
        private final ObservableList<AttendanceRecord> attendanceRecords = ObservableListUtil.arrayList();

        AddressBookStub(Collection<Person> persons,
                        Collection<Session> sessions, Collection<AttendanceRecord> attendanceRecords) {
            this.persons.setAll(persons);
            this.sessions.setAll(sessions);
            this.attendanceRecords.setAll(attendanceRecords);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Session> getSessionList() {
            return sessions;
        }

        @Override
        public ObservableList<AttendanceRecord> getAttendanceRecordsList() {
            return attendanceRecords;
        }

        @Override
        public int getNextPersonId() {
            return this.persons.size() + 1;
        }

        @Override
        public int getNextSessionId() {
            return this.sessions.size() + 1;
        }
    }
}

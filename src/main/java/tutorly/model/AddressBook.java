package tutorly.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import tutorly.commons.util.ToStringBuilder;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.attendancerecord.UniqueAttendanceRecordList;
import tutorly.model.person.Name;
import tutorly.model.person.Person;
import tutorly.model.person.UniquePersonList;
import tutorly.model.session.Session;
import tutorly.model.session.UniqueSessionList;

/**
 * Wraps all data at the address-book level.
 * Duplicates are not allowed.
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniquePersonList archivedPersons;
    private final UniqueSessionList sessions;
    private final UniqueAttendanceRecordList attendanceRecords;

    private int nextPersonId;
    private int nextSessionId;

    /**
     * Creates an AddressBook.
     */
    public AddressBook() {
        persons = new UniquePersonList();
        archivedPersons = new UniquePersonList();
        sessions = new UniqueSessionList();
        attendanceRecords = new UniqueAttendanceRecordList();

        nextPersonId = 1;
        nextSessionId = 1;
    }

    /**
     * Creates an AddressBook.
     */
    public AddressBook(int nextPersonId, int nextSessionId) {
        this();

        this.nextPersonId = nextPersonId;
        this.nextSessionId = nextSessionId;
    }

    /**
     * Creates an AddressBook using the ReadOnlyAddressBook in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setAll(persons);
    }

    /**
     * Replaces the contents of the session list with {@code sessions}.
     * {@code sessions} must not contain duplicate sessions.
     */
    public void setSessions(List<Session> sessions) {
        this.sessions.setAll(sessions);
    }

    /**
     * Replaces the contents of the attendance records list with {@code attendanceRecords}.
     */
    public void setAttendanceRecords(List<AttendanceRecord> attendanceRecords) {
        this.attendanceRecords.setAll(attendanceRecords);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setSessions(newData.getSessionList());
        setAttendanceRecords(newData.getAttendanceRecordsList());
        archivedPersons.clear();

        nextPersonId = newData.getNextPersonId();
        nextSessionId = newData.getNextSessionId();
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);

        if (p.getId() == 0) {
            // Set the student ID of the person if it has not been set
            p.setId(nextPersonId++);
        }
    }

    /**
     * Returns the person with the given ID if it exists in the persons address book.
     */
    public Optional<Person> getPersonById(int id) {
        return persons.getPersonById(id);
    }

    /**
     * Returns the person with the given name if it exists in the persons address book.
     */
    public Optional<Person> getPersonByName(Name name) {
        return persons.getPersonByName(name);
    }

    /**
     * Returns the person with the given ID if it exists in the archived persons address book.
     */
    public Optional<Person> getArchivedPersonById(int id) {
        return archivedPersons.getPersonById(id);
    }

    /**
     * Returns the person with the given name if it exists in the archived persons address book.
     */
    public Optional<Person> getArchivedPersonByName(Name name) {
        return archivedPersons.getPersonByName(name);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.set(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
        archivedPersons.add(key);
    }

    /**
     * Restores {@code key} from the archived persons list.
     * {@code key} must exist in the archived persons list.
     */
    public void restorePerson(Person key) {
        archivedPersons.remove(key);
        persons.add(key);
    }

    //// session-level operations

    /**
     * Returns true if a session with the same identity as {@code toCheck} exists in the address book.
     */
    public boolean hasSession(Session toCheck) {
        requireNonNull(toCheck);
        return sessions.contains(toCheck);
    }

    /**
     * Adds a session to the address book.
     * The session must not already exist in the address book.
     */
    public void addSession(Session s) {
        sessions.add(s);

        if (s.getId() == 0) {
            // Set the session ID of the session if it has not been set
            s.setId(nextSessionId++);
        }
    }

    /**
     * Returns the person with the given ID if it exists in the address book.
     */
    public Optional<Session> getSessionById(int id) {
        return sessions.getSessionById(id);
    }

    /**
     * Replaces the given session {@code target} in the list with {@code editedSession}.
     * {@code target} must exist in the address book.
     * The session identity of {@code editedSession} must not be the same as another session in the address book.
     */
    public void setSession(Session target, Session editedSession) {
        requireNonNull(editedSession);
        sessions.set(target, editedSession);
    }

    /**
     * Removes {@code session} from this {@code AddressBook}.
     * {@code session} must exist in the address book.
     */
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    //// attendance record-level operations

    /**
     * Returns true if an equivalent attendance record as {@code attendanceRecord} exists in the address book.
     */
    public boolean hasAttendanceRecord(AttendanceRecord attendanceRecord) {
        requireNonNull(attendanceRecord);
        return attendanceRecords.contains(attendanceRecord);
    }

    /**
     * Returns the attendance record equivalent to the given record.
     */
    public Optional<AttendanceRecord> findAttendanceRecord(AttendanceRecord attendanceRecord) {
        requireNonNull(attendanceRecord);
        return attendanceRecords.find(attendanceRecord);
    }

    /**
     * Adds an attendance record to the address book.
     */
    public void addAttendanceRecord(AttendanceRecord attendanceRecord) {
        attendanceRecords.add(attendanceRecord);
    }

    /**
     * Replaces the given attendance record {@code target} in the list with {@code editedAttendanceRecord}.
     * {@code target} must exist in the address book.
     */
    public void setAttendanceRecord(AttendanceRecord target, AttendanceRecord editedAttendanceRecord) {
        requireNonNull(editedAttendanceRecord);

        attendanceRecords.set(target, editedAttendanceRecord);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeAttendanceRecord(AttendanceRecord key) {
        attendanceRecords.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("sessions", sessions)
                .add("attendanceRecords", attendanceRecords)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Session> getSessionList() {
        return sessions.asUnmodifiableObservableList();
    }

    public ObservableList<Person> getArchivedPersonList() {
        return archivedPersons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<AttendanceRecord> getAttendanceRecordsList() {
        return attendanceRecords.asUnmodifiableObservableList();
    }

    @Override
    public int getNextPersonId() {
        return nextPersonId;
    }

    @Override
    public int getNextSessionId() {
        return nextSessionId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook otherAddressBook)) {
            return false;
        }

        return persons.equals(otherAddressBook.persons)
                && sessions.equals(otherAddressBook.sessions)
                && attendanceRecords.equals(otherAddressBook.attendanceRecords);
    }

    @Override
    public int hashCode() {
        return persons.hashCode() ^ sessions.hashCode() ^ attendanceRecords.hashCode();
    }
}

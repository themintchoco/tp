package tutorly.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import tutorly.commons.util.ToStringBuilder;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.attendancerecord.UniqueAttendanceRecordList;
import tutorly.model.person.Person;
import tutorly.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniquePersonList archivedPersons;
    private final UniqueAttendanceRecordList attendanceRecords;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        archivedPersons = new UniquePersonList();
        attendanceRecords = new UniqueAttendanceRecordList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
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
        this.persons.setPersons(persons);
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
        setAttendanceRecords(newData.getAttendanceRecordsList());
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
            p.setId(getTotalPersons());
        }
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
        archivedPersons.add(key);
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

    /**
     * Returns the total number of persons that have been added to the address book, both current and archived.
     */
    public int getTotalPersons() {
        return persons.size() + archivedPersons.size();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<AttendanceRecord> getAttendanceRecordsList() {
        return attendanceRecords.asUnmodifiableObservableList();
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

        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode() ^ archivedPersons.hashCode() ^ attendanceRecords.hashCode();
    }
}

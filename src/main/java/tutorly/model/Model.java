package tutorly.model;

import java.nio.file.Path;
import java.util.Optional;

import javafx.collections.ObservableList;
import tutorly.commons.core.GuiSettings;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.filter.Filter;
import tutorly.model.person.Identity;
import tutorly.model.person.Name;
import tutorly.model.person.Person;
import tutorly.model.session.Session;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Filter} that always evaluate to true
     */
    Filter<Person> FILTER_SHOW_ALL_PERSONS = ab -> p -> true;

    /**
     * {@code Filter} that always evaluate to true
     */
    Filter<Session> FILTER_SHOW_ALL_SESSIONS = ab -> s -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an optional of the person with the given id.
     */
    Optional<Person> getPersonById(long id);

    /**
     * Returns an optional of the person with the given name.
     */
    Optional<Person> getPersonByName(Name name);

    /**
     * Returns an optional of the person with the given identity consisting of either ID or name.
     */
    Optional<Person> getPersonByIdentity(Identity identity);

    /**
     * Returns an unmodifiable view of the person list
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the session list
     */
    ObservableList<Session> getSessionList();

    /**
     * Returns an unmodifiable view of the filtered session list
     */
    ObservableList<Session> getFilteredSessionList();

    /**
     * Returns an unmodifiable view of the attendance record list
     */
    ObservableList<AttendanceRecord> getAttendanceRecordList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code filter}.
     *
     * @throws NullPointerException if {@code filter} is null.
     */
    void updateFilteredPersonList(Filter<Person> filter);

    /**
     * Updates the filter of the filtered session list to filter by the given {@code filter}.
     *
     * @throws NullPointerException if {@code filter} is null.
     */
    void updateFilteredSessionList(Filter<Session> filter);

    /**
     * Returns true if a session with the same identity as {@code session} exists in the address book.
     */
    boolean hasSession(Session toCreate);

    /**
     * Returns true if the session to be created has overlapping timeslot with an existing session.
     */
    boolean hasOverlappingSession(Session toCreate);

    /**
     * Adds the given session.
     * {@code session} must not already exist in the address book.
     */
    void addSession(Session toCreate);

    /**
     * Deletes the given session.
     * {@code session} must already exist in the address book.
     */
    void deleteSession(Session target);

    /**
     *  Replaces the given session {@code session} with {@code editedSession}.
     * {@code session} must exist in the address book.
     */
    void setSession(Session session, Session editedSession);


    /**
     * Returns an optional of the session with the given id.
     */
    Optional<Session> getSessionById(long id);

    /**
     * Returns true if an AttendanceRecord with the same identity as
     * {@code record} exists in the address book.
     */
    boolean hasAttendanceRecord(AttendanceRecord record);

    /**
     * Returns the attendance record equivalent to the given record.
     */
    Optional<AttendanceRecord> findAttendanceRecord(AttendanceRecord record);

    /**
     * Adds the given AttendanceRecord.
     * {@code record} must not already exist in the address book.
     */
    void addAttendanceRecord(AttendanceRecord record);

    /**
     * Deletes the given AttendanceRecord.
     * {@code record} must already exist in the address book.
     */
    void removeAttendanceRecord(AttendanceRecord record);

    /* Replaces the given AttendanceRecord {@code target} with {@code editedRecord}.
     * {@code target} must exist in the address book.
     * The AttendanceRecord {@code editedRecord} must not be equivalent to another existing record.
     */
    void setAttendanceRecord(AttendanceRecord target, AttendanceRecord editedRecord);
}

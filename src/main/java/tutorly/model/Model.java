package tutorly.model;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import tutorly.commons.core.GuiSettings;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.filter.Filter;
import tutorly.model.person.Person;
import tutorly.model.session.Session;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Filter} that always evaluate to true */
    Filter<Person> FILTER_SHOW_ALL_PERSONS = ab -> p -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

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
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

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
     * Restores the given person.
     * The person must exist in the archived person list.
     */
    void restorePerson(Person target);

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

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the archived person list */
    ObservableList<Person> getArchivedPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code filter}.
     * @throws NullPointerException if {@code filter} is null.
     */
    void updateFilteredPersonList(Filter<Person> filter);


    /**
     * Returns true if a session with the same identity as {@code session} exists in the address book.
     */
    boolean hasSession(Session toCreate);

    /**
     * Adds the given session.
     * {@code session} must not already exist in the address book.
     */
    void addSession(Session toCreate);

    /**
     * Returns true if an AttendanceRecord with the same identity as
     * {@code record} exists in the address book.
     */
    boolean hasAttendanceRecord(AttendanceRecord record);

    /**
     * Adds the given AttendanceRecord.
     * {@code record} must not already exist in the address book.
     */
    void addAttendanceRecord(AttendanceRecord record);
}

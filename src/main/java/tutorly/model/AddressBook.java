package tutorly.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import tutorly.commons.util.ToStringBuilder;
import tutorly.model.person.Person;
import tutorly.model.person.UniquePersonList;
import tutorly.model.session.Session;
import tutorly.model.session.UniqueSessionList;

/**
 * Wraps all data at the address-book level.
 * Duplicates are not allowed (by .isSamePerson or .isSameSession comparison).
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueSessionList sessions;
    private final UniquePersonList archivedPersons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        sessions = new UniqueSessionList();
        archivedPersons = new UniquePersonList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons and Sessions in the {@code toBeCopied}
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
     * Replaces the contents of the session list with {@code sessions}.
     * {@code sessions} must not contain duplicate sessions.
     */
    public void setSessions(List<Session> sessions) {
        this.sessions.setSessions(sessions);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setSessions(newData.getSessionList());
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
    }

    /**
     * Removes {@code session} from this {@code AddressBook}.
     * {@code session} must exist in the address book.
     */
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    /**
     * Replaces the given session {@code target} in the list with {@code editedSession}.
     * {@code target} must exist in the address book.
     * The session identity of {@code editedSession} must not be the same as another session in the address book.
     */
    public void setSession(Session target, Session editedSession) {
        requireNonNull(editedSession);
        sessions.setSession(target, editedSession);
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
                .add("sessions", sessions)
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
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook otherAddressBook)) {
            return false;
        }

        return persons.equals(otherAddressBook.persons) && sessions.equals(otherAddressBook.sessions);
    }

    @Override
    public int hashCode() {
        return persons.hashCode() + sessions.hashCode();
    }
}

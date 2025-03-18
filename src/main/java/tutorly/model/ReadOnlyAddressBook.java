package tutorly.model;

import java.util.List;

import javafx.collections.ObservableList;
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

    ObservableList<Session> getSessionList();
}

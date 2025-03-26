package tutorly.model.person;

import java.util.Optional;

import tutorly.model.uniquelist.UniqueList;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList extends UniqueList<Person> {

    @Override
    protected boolean isDistinct(Person a, Person b) {
        return !a.isSamePerson(b);
    }

    /**
     * Returns the person with the given ID if it exists.
     *
     * @param id The ID of the person to retrieve.
     * @return The person with the given ID.
     */
    public Optional<Person> getPersonById(int id) {
        return internalList.stream()
                .filter(person -> person.getId() == id)
                .findFirst();
    }

    /**
     * Returns the person with the given name if it exists.
     *
     * @param name The name of the person to retrieve.
     * @return The person with the given name.
     */
    public Optional<Person> getPersonByName(Name name) {
        return internalList.stream()
                .filter(person -> person.getName().equals(name))
                .findFirst();
    }

}

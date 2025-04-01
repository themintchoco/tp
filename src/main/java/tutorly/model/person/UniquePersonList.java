package tutorly.model.person;

import java.util.Optional;

import tutorly.model.uniquelist.UniqueList;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList extends UniqueList<Person> {

    @Override
    protected boolean isEquivalent(Person a, Person b) {
        return a.isSamePerson(b);
    }

    @Override
    protected int compare(Person a, Person b) {
        return Integer.compare(a.getId(), b.getId());
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

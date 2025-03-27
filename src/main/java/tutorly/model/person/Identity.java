package tutorly.model.person;

import java.util.Objects;
import java.util.Optional;

import tutorly.commons.util.ToStringBuilder;
import tutorly.model.Model;

/**
 * Represents a Person's identity.
 * Since a person's ID and Name are unique in the address book, they can be used to identify a Person.
 */
public class Identity {

    public static final String MESSAGE_INVALID_ID = "Person ID must be a positive integer.";

    private int id;
    private Name name;

    /**
     * Creates an identity with the given name.
     *
     * @param name The name of the person.
     */
    public Identity(Name name) {
        this.name = name;
    }

    /**
     * Creates an identity with the given ID.
     *
     * @param id The ID of the person.
     */
    public Identity(int id) {
        if (id < 1) {
            throw new IllegalArgumentException(MESSAGE_INVALID_ID);
        }

        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * Checks if the person ID is provided in the identity.
     *
     * @return True if the ID is present in the identity, false otherwise.
     */
    public boolean isIdPresent() {
        return id != 0;
    }

    public Name getName() {
        return name;
    }

    /**
     * Checks if the person name is provided in the identity.
     *
     * @return True if the name is present in the identity, false otherwise.
     */
    public boolean isNamePresent() {
        return name != null;
    }

    /**
     * Returns the person with the identity if it exists in the model.
     * If both ID and name are present in the identity, ID will be used instead.
     *
     * @param model The model to retrieve the person from.
     * @return The person with the given ID or name if it exists in the model.
     */
    public Optional<Person> getPerson(Model model) {
        if (isIdPresent()) {
            return model.getPersonById(id);
        } else if (isNamePresent()) {
            return model.getPersonByName(name);
        }

        return Optional.empty();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Identity otherIdentity)) {
            return false;
        }

        return isIdPresent() == otherIdentity.isIdPresent()
                && id == otherIdentity.id
                && isNamePresent() == otherIdentity.isNamePresent()
                && Objects.equals(name, otherIdentity.name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", id)
                .add("name", name)
                .toString();
    }
}

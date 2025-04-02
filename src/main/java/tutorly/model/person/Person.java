package tutorly.model.person;

import static tutorly.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import tutorly.commons.util.ToStringBuilder;
import tutorly.model.AddressBook;
import tutorly.model.tag.Tag;

/**
 * Represents a student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 * Optional fields with empty string values are considered as not provided.
 */
public class Person {

    public static final String MESSAGE_REASSIGNED_ID = "Student ID has already been set for this person.";
    public static final String MESSAGE_INVALID_ID = "Student ID must be a positive integer.";

    // Identity fields
    private long id; // id field is effectively final
    private final Name name;

    // Data fields
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Memo memo;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Memo memo) {
        requireAllNonNull(name, phone, email, address, tags, memo);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.memo = memo;
    }

    /**
     * Sets the student ID assigned by the address book during {@link AddressBook#addPerson(Person)}. Should only be
     * called once per student as the student ID is effectively final.
     */
    public void setId(long studentId) {
        if (this.id != 0) {
            throw new IllegalStateException(MESSAGE_REASSIGNED_ID);
        }

        if (studentId < 1) {
            throw new IllegalArgumentException(MESSAGE_INVALID_ID);
        }

        this.id = studentId;
    }

    public long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Memo getMemo() {
        return memo;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person otherPerson)) {
            return false;
        }

        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && memo.equals(otherPerson.memo);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, name, phone, email, address, tags, memo);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", id)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("memo", memo)
                .toString();
    }

}

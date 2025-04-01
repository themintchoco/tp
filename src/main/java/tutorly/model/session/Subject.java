package tutorly.model.session;

import static java.util.Objects.requireNonNull;
import static tutorly.commons.util.AppUtil.checkArgument;

/**
 * Represents a Subject in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubject(String)}
 */
public class Subject {

    public static final String MESSAGE_CONSTRAINTS =
            "Subjects should only contain letters, numbers, spaces, and the following symbols: & + , -"
            + " and it should not be blank. It should also not exceed 20 characters.";
    public static final int MAX_LENGTH = 20;

    /*
     * The first character of the subject must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\s][A-Za-z0-9 &+,-]*";

    public final String subjectName;

    /**
     * Constructs a {@code Subject}.
     *
     * @param subjectName A valid subject name.
     */
    public Subject(String subjectName) {
        requireNonNull(subjectName);
        checkArgument(isValidSubject(subjectName), MESSAGE_CONSTRAINTS);
        this.subjectName = subjectName;
    }

    /**
     * Returns true if a given string is a valid subject name.
     */
    public static boolean isValidSubject(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= MAX_LENGTH;
    }

    @Override
    public String toString() {
        return subjectName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Subject otherSubject)) {
            return false;
        }

        return subjectName.equals(otherSubject.subjectName);
    }

    @Override
    public int hashCode() {
        return subjectName.hashCode();
    }
}

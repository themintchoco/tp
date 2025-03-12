package tutorly.model.person;

import static java.util.Objects.requireNonNull;
import static tutorly.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's memo in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMemo(String)}
 */
public class Memo {

    public static final String MESSAGE_CONSTRAINTS = "Memo can take any values, and it should not be blank";

    /*
     * The first character of the memo must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Memo}.
     *
     * @param memo A valid memo.
     */
    public Memo(String memo) {
        requireNonNull(memo);
        checkArgument(isValidMemo(memo), MESSAGE_CONSTRAINTS);
        value = memo;
    }

    /**
     * Returns true if a given string is a valid memo.
     */
    public static boolean isValidMemo(String test) {
        return test.isEmpty() || test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Memo otherMemo)) {
            return false;
        }

        return value.equals(otherMemo.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

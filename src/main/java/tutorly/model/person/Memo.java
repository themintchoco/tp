package tutorly.model.person;

import static java.util.Objects.requireNonNull;
import static tutorly.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's memo in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMemo(String)}
 */
public class Memo {

    public static final int MAX_LENGTH = 200;

    public static final String MESSAGE_CONSTRAINTS = "Memo can take any values, and it should not be blank. "
            + "The maximum length is " + MAX_LENGTH + " characters.";

    /*
     * The first character of the memo must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input. Newlines are allowed.
     */
    public static final String VALIDATION_REGEX = "[^\\s](?s).*";

    private static final Memo EMPTY_MEMO = new Memo();

    public final String value;

    /**
     * Constructs an empty {@code Memo} instance.
     */
    private Memo() {
        value = "";
    }

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
     * Returns an empty Memo instance.
     */
    public static Memo empty() {
        return EMPTY_MEMO;
    }

    /**
     * Returns true if a given string is a valid memo.
     */
    public static boolean isValidMemo(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= MAX_LENGTH;
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

package tutorly.model.attendancerecord;

import static java.util.Objects.requireNonNull;
import static tutorly.commons.util.AppUtil.checkArgument;

/**
 * Represents a feedback in AttendanceRecord in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFeedback(String)}
 */
public class Feedback {
    public static final int MAX_LENGTH = 200;

    public static final String MESSAGE_CONSTRAINTS = "Feedback can take any values. "
            + "The maximum length is " + MAX_LENGTH + " characters.";

    /*
     * The first character of the feedback must not be a whitespace. Newlines are allowed.
     * Fully empty strings are allowed too.
     */
    public static final String VALIDATION_REGEX = "^$|[^\\s](?s).*";

    private static final Feedback EMPTY_FEEDBACK = new Feedback();

    public final String value;

    /**
     * Constructs an empty {@code Feedback} instance.
     */
    private Feedback() {
        value = "";
    }

    /**
     * Constructs a {@code Feedback}.
     *
     * @param feedback A valid feedback.
     */
    public Feedback(String feedback) {
        requireNonNull(feedback);
        checkArgument(isValidFeedback(feedback), MESSAGE_CONSTRAINTS);
        value = feedback;
    }

    /**
     * Returns an empty Feedback instance.
     */
    public static Feedback empty() {
        return EMPTY_FEEDBACK;
    }

    /**
     * Returns true if a given string is a valid feedback.
     */
    public static boolean isValidFeedback(String test) {
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
        if (!(other instanceof Feedback otherFeedback)) {
            return false;
        }
        return value.equals(otherFeedback.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

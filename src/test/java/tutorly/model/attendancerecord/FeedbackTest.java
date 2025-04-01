package tutorly.model.attendancerecord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FeedbackTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Feedback(null));
    }

    @Test
    public void constructor_invalidFeedback_throwsIllegalArgumentException() {
        String invalidFeedback = "";
        assertThrows(IllegalArgumentException.class, () -> new Feedback(invalidFeedback));
    }

    @Test
    public void isValidFeedback() {
        // null feedback
        assertThrows(NullPointerException.class, () -> Feedback.isValidFeedback(null));

        // invalid feedbacks
        assertFalse(Feedback.isValidFeedback("")); // empty string
        assertFalse(Feedback.isValidFeedback(" ")); // spaces only
        assertFalse(Feedback.isValidFeedback("a".repeat(Feedback.MAX_LENGTH) + "a")); // extra long feedback

        // valid feedbacks
        assertTrue(Feedback.isValidFeedback("Needs to improve on his English")); // alphabets only
        assertTrue(Feedback.isValidFeedback("-")); // one character
        assertTrue(Feedback.isValidFeedback("""
                This feedback covers several points:
                1) Overall academic performance.
                2) Areas of improvement such as organization, clarity,
                   and depth of analysis.
                3) Suggested resources for skill enhancement.
                """)); // long feedback
        assertTrue(Feedback.isValidFeedback("a".repeat(Feedback.MAX_LENGTH))); // long feedback
        assertTrue(Feedback.isValidFeedback("This is a feedback with special "
                + "characters: @#$%^&*()")); // special characters
        assertTrue(Feedback.isValidFeedback("This is a feedback with numbers: 1234567890")); // numbers
        assertTrue(Feedback.isValidFeedback("This is a feedback with emojis: 😊👍")); // emojis
        assertTrue(Feedback.isValidFeedback("This is a feedback with line breaks:\n"
                + "Line 1\n"
                + "Line 2\n"
                + "Line 3")); // line breaks
        assertTrue(Feedback.isValidFeedback("This is a feedback with tabs:\t"
                + "Tab 1\t"
                + "Tab 2\t"
                + "Tab 3")); // tabs
        assertTrue(Feedback.isValidFeedback("This is a feedback with spaces:    ")); // spaces
    }

    @Test
    public void equals() {
        Feedback feedback1 = new Feedback("Good job!");
        Feedback feedback2 = new Feedback("Needs improvement");
        Feedback feedback3 = new Feedback("Good job!");

        // same object -> returns true
        assertEquals(feedback1, feedback1);

        // same values -> returns true
        assertEquals(feedback1, feedback3);

        // different types -> returns false
        assertNotEquals(new Object(), feedback1);

        // null -> returns false
        assertNotEquals(null, feedback1);

        // different feedback -> returns false
        assertNotEquals(feedback1, feedback2);
    }
}

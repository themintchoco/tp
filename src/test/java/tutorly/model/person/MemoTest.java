package tutorly.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MemoTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Memo(null));
    }

    @Test
    public void constructor_invalidMemo_throwsIllegalArgumentException() {
        String invalidMemo = "";
        assertThrows(IllegalArgumentException.class, () -> new Memo(invalidMemo));
    }

    @Test
    public void isValidMemo() {
        // null memo
        assertThrows(NullPointerException.class, () -> Memo.isValidMemo(null));

        // invalid memos
        assertFalse(Memo.isValidMemo("")); // empty string
        assertFalse(Memo.isValidMemo(" ")); // spaces only
        assertFalse(Memo.isValidMemo("a".repeat(Memo.MAX_LENGTH) + "a")); // extra long memo

        // valid memos
        assertTrue(Memo.isValidMemo("Needs to improve on his English")); // alphabets only
        assertTrue(Memo.isValidMemo("-")); // one character

        String longMemo = "This memo covers several points:\n"
                + "1) Overall academic performance.\n"
                + "2) Areas of improvement such as organization, clarity,\n"
                + "   and depth of analysis.\n"
                + "3) Suggested resources for skill enhancement.\n";
        assertTrue(Memo.isValidMemo(longMemo)); // long memo
        assertTrue(Memo.isValidMemo("a".repeat(Memo.MAX_LENGTH))); // long memo
        assertTrue(Memo.isValidMemo("This is a memo with special characters: @#$%^&*()")); // special characters
        assertTrue(Memo.isValidMemo("This is a memo with numbers: 1234567890")); // numbers
        assertTrue(Memo.isValidMemo("This is a memo with emojis: 😊👍")); // emojis
        assertTrue(Memo.isValidMemo("This is a memo with line breaks:\n"
                + "Line 1\n"
                + "Line 2\n"
                + "Line 3")); // line breaks
        assertTrue(Memo.isValidMemo("This is a memo with tabs:\t"
                + "Tab 1\t"
                + "Tab 2\t"
                + "Tab 3")); // tabs
        assertTrue(Memo.isValidMemo("This is a memo with spaces:    ")); // spaces

    }

    @Test
    public void equals() {
        Memo memo = new Memo("Valid Memo");

        // same values -> returns true
        assertEquals(memo, new Memo("Valid Memo"));

        // same object -> returns true
        assertEquals(memo, memo);

        // null -> returns false
        assertNotEquals(null, memo);

        // different types -> returns false
        assertFalse(memo.equals(5.0f));

        // different values -> returns false
        assertNotEquals(memo, new Memo("Other Valid Memo"));
    }
}

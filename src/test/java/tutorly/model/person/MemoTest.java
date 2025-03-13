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

        // valid memos
        assertTrue(Memo.isValidMemo("Needs to improve on his English")); // alphabets only
        assertTrue(Memo.isValidMemo("-")); // one character
        assertTrue(Memo.isValidMemo("""
                This memo covers several points:
                1) Overall academic performance.
                2) Areas of improvement such as organization, clarity,
                   and depth of analysis.
                3) Suggested resources for skill enhancement.
                
                Additional notes: Student has shown consistent growth \
                since the last meeting but needs more practice on complex \
                problem-solving tasks. Further guidance on research \
                techniques and project management is recommended. \
                Meeting again in two weeks to evaluate progress.
                """)); // long memo
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

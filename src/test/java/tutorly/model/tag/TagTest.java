package tutorly.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag names
        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName(" ")); // spaces only
        assertFalse(Tag.isValidTagName("a".repeat(Tag.MAX_LENGTH) + "a")); // extra long tag name

        // valid tag names
        assertTrue(Tag.isValidTagName("tag_name")); // special characters
        assertTrue(Tag.isValidTagName("%G#!F*{} <>!sd65")); // special characters
        assertTrue(Tag.isValidTagName("tagname")); // alphabets only
        assertTrue(Tag.isValidTagName("tag123")); // alphanumeric
        assertTrue(Tag.isValidTagName("a".repeat(Tag.MAX_LENGTH))); // long tag name

    }

}

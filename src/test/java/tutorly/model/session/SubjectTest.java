package tutorly.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class SubjectTest {

    @Test
    public void constructor_validInput_success() {
        Subject subject = new Subject("Mathematics");
        assertEquals("Mathematics", subject.subjectName);
    }

    @Test
    public void equals_sameObject_true() {
        Subject subject = new Subject("Mathematics");
        assertEquals(subject, subject);
    }

    @Test
    public void equals_differentObjectSameValues_true() {
        Subject subject1 = new Subject("Mathematics");
        Subject subject2 = new Subject("Mathematics");
        assertEquals(subject1, subject2);
    }

    @Test
    public void equals_differentObjectDifferentValues_false() {
        Subject subject1 = new Subject("Mathematics");
        Subject subject2 = new Subject("Science");
        assertNotEquals(subject1, subject2);
    }

    @Test
    public void toString_correctFormat() {
        Subject subject = new Subject("Mathematics");
        String expected = "Mathematics";
        assertEquals(expected, subject.toString());
    }
}

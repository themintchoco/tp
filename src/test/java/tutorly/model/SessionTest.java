package tutorly.model;

// Standard Java imports
import java.time.LocalDate;

// Static imports should come after standard imports
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

// Regular imports should come after static imports
import org.junit.jupiter.api.Test;

class SessionTest {

    @Test
    void testSessionConstructorAndGetters() {
        int studentId = 12345;
        LocalDate date = LocalDate.of(2025, 3, 12);
        Session session = new Session(studentId, date);

        assertEquals(studentId, session.getStudentId());
        assertEquals(date, session.getDate());
    }

    @Test
    void testToString() {
        Session session = new Session(67890, LocalDate.of(2025, 3, 15));
        String expected = "Session{studentId=67890, date=2025-03-15}";
        assertEquals(expected, session.toString());
    }

    @Test
    void testDifferentDates() {
        Session session1 = new Session(123, LocalDate.of(2025, 3, 12));
        Session session2 = new Session(123, LocalDate.of(2025, 3, 13));

        assertNotEquals(session1, session2);
    }

    @Test
    void testHashCodeConsistency() {
        Session session = new Session(789, LocalDate.of(2025, 3, 12));
        int hash1 = session.hashCode();
        int hash2 = session.hashCode();

        assertEquals(hash1, hash2);
    }
}

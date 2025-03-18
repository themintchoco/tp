package tutorly.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import tutorly.model.session.Session;

class SessionTest {

    @Test
    void testSessionConstructorAndGetters() {
        int studentId = 12345;
        LocalDate date = LocalDate.of(2025, 3, 12);
        String subject = "Math";
        Session session = new Session(studentId, date, subject);

        assertEquals(studentId, session.getStudentId());
        assertEquals(date, session.getDate());
        assertEquals(subject, session.getSubject());
    }

    @Test
    void testToString() {
        Session session = new Session(67890, LocalDate.of(2025, 3, 15), "Physics");
        String expected = "Session{studentId=67890, sessionId="
                + session.getSessionId() + ", date=2025-03-15, subject=Physics}";
        assertEquals(expected, session.toString());
    }

    @Test
    void testDifferentDates() {
        Session session1 = new Session(123, LocalDate.of(2025, 3, 12), "Math");
        Session session2 = new Session(123, LocalDate.of(2025, 3, 13), "Math");

        assertNotEquals(session1, session2);
    }

    @Test
    void testHashCodeConsistency() {
        Session session = new Session(789, LocalDate.of(2025, 3, 12), "English");
        int hash1 = session.hashCode();
        int hash2 = session.hashCode();

        assertEquals(hash1, hash2);
    }

    @Test
    void testIncrementalSessionId() {
        Session session1 = new Session(123, LocalDate.of(2025, 3, 12), "Math");
        Session session2 = new Session(123, LocalDate.of(2025, 3, 13), "Math");

        assertEquals(session1.getSessionId() + 1, session2.getSessionId());

    }
}

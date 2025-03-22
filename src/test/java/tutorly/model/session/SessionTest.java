package tutorly.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Test class for Session.
 */
public class SessionTest {

    @Test
    public void constructor_validInput_success() {
        LocalDate date = LocalDate.of(2025, 3, 20);
        Session session = new Session(1, date, "Mathematics");

        assertEquals(1, session.getSessionId());
        assertEquals(date, session.getDate());
        assertEquals("Mathematics", session.getSubject());
    }

    @Test
    public void equals_sameObject_true() {
        LocalDate date = LocalDate.of(2025, 3, 20);
        Session session = new Session(1, date, "Mathematics");
        assertEquals(session, session);
    }

    @Test
    public void equals_differentObjectSameValues_false() {
        LocalDate date = LocalDate.of(2025, 3, 20);
        Session session1 = new Session(1, date, "Mathematics");
        Session session2 = new Session(1, date, "Mathematics");
        assertEquals(session1, session2);
    }

    @Test
    public void toString_correctFormat() {
        LocalDate date = LocalDate.of(2025, 3, 20);
        Session session = new Session(1, date, "Mathematics");

        String expected = "Session{sessionId=" + session.getSessionId()
                + ", date=2025-03-20, subject=Mathematics}";
        assertEquals(expected, session.toString());
    }

    @Test
    public void setId_validId_success() {
        LocalDate date = LocalDate.of(2025, 3, 20);
        Session session = new Session(0, date, "Mathematics"); // Assume session ID is initially unset (0)
        session.setId(1);
        assertEquals(1, session.getSessionId());
    }
}

package tutorly.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * Test class for Session.
 */
public class SessionTest {
    public static final Timeslot TIMESLOT = new Timeslot(LocalDateTime.of(2025, 3, 25, 10, 0),
            LocalDateTime.of(2025, 3, 25, 12, 0));
    public static final Subject SUBJECT = new Subject("Mathematics");

    @Test
    public void constructor_validInput_success() {
        Session session = new Session(TIMESLOT, SUBJECT);

        assertEquals(0, session.getId());
        assertEquals(TIMESLOT, session.getTimeslot());
        assertEquals(SUBJECT, session.getSubject());
    }

    @Test
    public void equals_sameObject_true() {
        Session session = new Session(TIMESLOT, SUBJECT);
        assertEquals(session, session);
    }

    @Test
    public void equals_differentObjectSameValues_true() {
        Session session1 = new Session(TIMESLOT, SUBJECT);
        Session session2 = new Session(TIMESLOT, SUBJECT);
        assertEquals(session1, session2);
    }

    @Test
    public void toString_correctFormat() {
        Session session = new Session(TIMESLOT, SUBJECT);
        String expected = Session.class.getCanonicalName() + "{id=" + session.getId()
                + ", timeslot=" + session.getTimeslot() + ", subject=" + session.getSubject() + "}";
        assertEquals(expected, session.toString());
    }

    @Test
    public void setId_validId_success() {
        Session session = new Session(TIMESLOT, SUBJECT); // Assume session ID is initially unset (0)
        assertEquals(0, session.getId());
        session.setId(1);
        assertEquals(1, session.getId());
    }
}

package tutorly.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutorly.model.session.exceptions.DuplicateSessionException;
import javafx.collections.ObservableList;
import tutorly.model.session.exceptions.SessionNotFoundException;

/**
 * Test class for UniqueSessionList.
 */
public class UniqueSessionListTest {

    private UniqueSessionList sessionList;
    private Session session1;
    private Session session2;
    private Session session3;

    @BeforeEach
    void setUp() {
        sessionList = new UniqueSessionList();
        session1 = new Session(101, LocalDate.of(2024, 3, 20), "Math");
        session2 = new Session(102, LocalDate.of(2024, 3, 21), "Science");
        session3 = new Session(103, LocalDate.of(2024, 3, 22), "English");
    }

    @Test
    void testAddSuccess() {
        sessionList.add(session1);
        assertTrue(sessionList.contains(session1));
    }

    @Test
    void testAddDuplicateThrowsException() {
        sessionList.add(session1);
        assertThrows(DuplicateSessionException.class, () -> sessionList.add(session1));
    }

    @Test
    void testRemoveSuccess() {
        sessionList.add(session1);
        sessionList.remove(session1);
        assertFalse(sessionList.contains(session1));
    }

    @Test
    void testRemoveNonExistentThrowsException() {
        assertThrows(SessionNotFoundException.class, () -> sessionList.remove(session1));
    }

    @Test
    void testSetSessionSuccess() {
        sessionList.add(session1);
        sessionList.setSession(session1, session2);
        assertFalse(sessionList.contains(session1));
        assertTrue(sessionList.contains(session2));
    }

    @Test
    void testSetSessionTargetNotFoundThrowsException() {
        assertThrows(SessionNotFoundException.class, () -> sessionList.setSession(session1, session2));
    }

    @Test
    void testSetSessionDuplicateThrowsException() {
        sessionList.add(session1);
        sessionList.add(session2);
        assertThrows(DuplicateSessionException.class, () -> sessionList.setSession(session1, session2));
    }

    @Test
    void testSetSessionsSuccess() {
        List<Session> newSessions = List.of(session1, session2);
        sessionList.setSessions(newSessions);
        assertEquals(2, sessionList.asUnmodifiableObservableList().size());
    }

    @Test
    void testSetSessionsDuplicateThrowsException() {
        List<Session> duplicateSessions = List.of(session1, session1);
        assertThrows(DuplicateSessionException.class, () -> sessionList.setSessions(duplicateSessions));
    }

    @Test
    void testAsUnmodifiableObservableList() {
        sessionList.add(session1);
        ObservableList<Session> unmodifiableList = sessionList.asUnmodifiableObservableList();
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add(session2));
    }
}

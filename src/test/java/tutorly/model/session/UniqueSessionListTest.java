package tutorly.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import tutorly.model.uniquelist.exceptions.DuplicateElementException;
import tutorly.model.uniquelist.exceptions.ElementNotFoundException;
import tutorly.testutil.SessionBuilder;

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
        Timeslot timeslot1 = new Timeslot(LocalDateTime.of(2025, 3, 25, 10, 0),
                LocalDateTime.of(2025, 3, 25, 12, 0));
        Timeslot timeslot2 = new Timeslot(LocalDateTime.of(2025, 3, 25, 11, 0),
                LocalDateTime.of(2025, 3, 25, 13, 0));
        Timeslot timeslot3 = new Timeslot(LocalDateTime.of(2025, 3, 25, 12, 0),
                LocalDateTime.of(2025, 3, 25, 13, 0));

        session1 = new SessionBuilder().withId(1).withTimeslot(timeslot1).withSubject("Math").build();
        session2 = new SessionBuilder().withId(2).withTimeslot(timeslot2).withSubject("Science").build();
        session3 = new SessionBuilder().withId(3).withTimeslot(timeslot3).withSubject("English").build();
    }

    @Test
    void testAddSuccess() {
        sessionList.add(session1);
        sessionList.add(session3);
        assertTrue(sessionList.contains(session1));
        assertTrue(sessionList.contains(session3));
    }

    @Test
    void testAddDuplicateThrowsException() {
        sessionList.add(session1);
        assertThrows(DuplicateElementException.class, () -> sessionList.add(session1));
    }

    @Test
    void testAddOverlappingTimeslotThrowsException() {
        sessionList.add(session1);
        Session session = new SessionBuilder().withId(1).withTimeslot(session2.getTimeslot()).build();
        assertThrows(DuplicateElementException.class, () -> sessionList.add(session));
    }

    @Test
    void testRemoveSuccess() {
        sessionList.add(session1);
        sessionList.remove(session1);
        assertFalse(sessionList.contains(session1));
    }

    @Test
    void testRemoveNonExistentThrowsException() {
        assertThrows(ElementNotFoundException.class, () -> sessionList.remove(session1));
    }

    @Test
    void testSetSessionSuccess() {
        sessionList.add(session1);
        sessionList.set(session1, session2);
        assertFalse(sessionList.contains(session1));
        assertTrue(sessionList.contains(session2));
    }

    @Test
    void testSetSessionTargetNotFoundThrowsException() {
        assertThrows(ElementNotFoundException.class, () -> sessionList.set(session1, session2));
    }

    @Test
    void testSetSessionDuplicateThrowsException() {
        sessionList.add(session1);
        sessionList.add(session2);
        assertThrows(DuplicateElementException.class, () -> sessionList.set(session1, session2));
    }

    @Test
    void testSetSessionsSuccess() {
        List<Session> newSessions = List.of(session1, session3);
        sessionList.setAll(newSessions);
        assertEquals(2, sessionList.asUnmodifiableObservableList().size());
    }

    @Test
    void testSetSessionsDuplicateThrowsException() {
        List<Session> duplicateSessions = List.of(session1, session1);
        assertThrows(DuplicateElementException.class, () -> sessionList.setAll(duplicateSessions));
    }

    @Test
    void testAsUnmodifiableObservableList() {
        sessionList.add(session1);
        ObservableList<Session> unmodifiableList = sessionList.asUnmodifiableObservableList();
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add(session2));
    }

    @Test
    void testIterator() {
        sessionList.add(session1);
        sessionList.add(session3);

        Iterator<Session> iterator = sessionList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(session1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(session3, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testEqualsAndHashCode() {
        UniqueSessionList anotherSessionList = new UniqueSessionList();
        assertEquals(sessionList, anotherSessionList); // Empty lists should be equal

        sessionList.add(session1);
        anotherSessionList.add(session1);
        assertEquals(sessionList, anotherSessionList); // Lists with the same content should be equal

        sessionList.add(session3);
        assertNotEquals(sessionList, anotherSessionList); // Different lists should not be equal

        assertEquals(sessionList.hashCode(), sessionList.hashCode()); // Same object should have the same hashcode
    }

    @Test
    void testToString() {
        sessionList.add(session1);
        sessionList.add(session3);
        String expectedString = "[" + session1.toString() + ", " + session3.toString() + "]";
        assertEquals(expectedString, sessionList.toString());
    }
}

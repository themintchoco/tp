package tutorly.model.session;

import java.util.Optional;

import tutorly.model.person.Person;
import tutorly.model.uniquelist.UniqueList;

/**
 * A list of sessions that enforces uniqueness between its elements and does not allow nulls.
 * A session is considered unique by comparing using {@code Session#isSameSession(Session)}.
 *
 * Supports a minimal set of list operations.
 *
 * @see Session#equals(Object)
 */
public class UniqueSessionList extends UniqueList<Session> {

    @Override
    protected boolean isDistinct(Session a, Session b) {
        return !a.isSameSession(b);
    }

    /**
     * Returns the session with the given ID if it exists.
     *
     * @param id The ID of the session to retrieve.
     * @return The session with the given ID.
     */
    public Optional<Session> getSessionById(int id) {
        return internalList.stream()
                .filter(session -> session.getId() == id)
                .findFirst();
    }
}

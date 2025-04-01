package tutorly.model.session;

import java.util.Optional;

import tutorly.model.uniquelist.UniqueList;

/**
 * A list of sessions that enforces uniqueness between its elements and does not allow nulls.
 * A session is considered unique by comparing using {@code Session#isSameSession(Session)}.
 *
 * @see Session#isSameSession(Session)
 */
public class UniqueSessionList extends UniqueList<Session> {

    @Override
    protected boolean isEquivalent(Session a, Session b) {
        return a.isSameSession(b);
    }

    @Override
    protected int compare(Session a, Session b) {
        return Integer.compare(a.getId(), b.getId());
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

    /**
     * Returns true if the session {@code toCheck} overlaps with any existing sessions in the list.
     * This is done by checking if the timeslot of the session overlaps with any other session's timeslot.
     * Conflicts with the same session are ignored.
     *
     * @param toCheck The session to check for overlap.
     * @return True if there is an overlapping session, false otherwise.
     */
    public boolean hasOverlappingSession(Session toCheck) {
        return internalList.stream()
                .anyMatch(session -> !session.isSameSession(toCheck)
                        && session.hasOverlappingTimeslot(toCheck));
    }
}

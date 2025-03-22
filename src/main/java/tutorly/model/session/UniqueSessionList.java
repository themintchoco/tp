package tutorly.model.session;

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

}

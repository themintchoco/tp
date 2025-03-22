package tutorly.model.session;
import java.time.LocalDate;

/**
 * Represents a tutoring session for a student.
 */
public class Session {
    private int id; // id field is effectively final
    private final LocalDate date;
    private final String subject;

    /**
     * Constructs a new Session with an auto-incremented session ID.
     *
     * @param id The unique identifier of the student.
     * @param date The date of the session.
     * @param subject The subject of the session.
     */
    public Session(int id, LocalDate date, String subject) {
        this.id = id;
        this.date = date;
        this.subject = subject;
    }

    /**
     * Sets the session ID of the session.
     * @param id The session ID to set.
     */
    public void setId(int id) {
        if (this.id != 0) {
            throw new IllegalStateException("Session ID has already been set for this session.");
        }

        if (id < 1) {
            throw new IllegalArgumentException("Session ID must be a positive integer.");
        }

        this.id = id;
    }

    /**
     * Gets the session ID associated with this session.
     *
     * @return The session ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the session date.
     *
     * @return The date of the session.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the subject of the session.
     *
     * @return The subject of the session.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Returns true if both sessions have the same identity and data fields.
     * This defines a stronger notion of equality between two sessions.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Session otherSession)) {
            return false;
        }

        return id == otherSession.id
                && date.equals(otherSession.date)
                && subject.equals(otherSession.subject);
    }

    /**
     * Returns a string representation of the session.
     *
     * @return A formatted string with student ID, session ID, date, and subject.
     */
    @Override
    public String toString() {
        return "Session{sessionId="
                + id + ", date=" + date + ", subject=" + subject + "}";
    }
}

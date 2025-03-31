package tutorly.model.session;

import java.time.LocalDate;

import tutorly.commons.util.ToStringBuilder;
import tutorly.model.AddressBook;

/**
 * Represents a tutoring session for a student.
 */
public class Session {

    public static final String MESSAGE_REASSIGNED_ID = "Session ID has already been set for this session.";
    public static final String MESSAGE_INVALID_ID = "Session ID must be a positive integer.";

    private int id; // id field is effectively final
    private final Timeslot timeslot;
    private final String subject;

    /**
     * Constructs a new Session. Every field must be present and not null.
     *
     * @param timeslot The start and end datetime of the session.
     * @param subject The subject of the session.
     */
    public Session(Timeslot timeslot, String subject) {
        this.timeslot = timeslot;
        this.subject = subject;
    }

    /**
     * Sets the session ID assigned by the address book during {@link AddressBook#addSession(Session)}. Should only be
     * called once per session as the session ID is effectively final.
     */
    public void setId(int id) {
        if (this.id != 0) {
            throw new IllegalStateException(MESSAGE_REASSIGNED_ID);
        }

        if (id < 1) {
            throw new IllegalArgumentException(MESSAGE_INVALID_ID);
        }

        this.id = id;
    }

    /**
     * Returns the date of the session based on its start time.
     */
    public LocalDate getDate() {
        return timeslot.getStartTime().toLocalDate();
    }

    public int getId() {
        return id;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public String getSubject() {
        return subject;
    }

    /**
     * Returns true if both sessions have the same ID and have overlapping timeslots.
     * This defines a weaker notion of equality between two sessions.
     */
    public boolean isSameSession(Session otherSession) {
        if (otherSession == this) {
            return true;
        }

        return otherSession != null
                && id == otherSession.id
                && timeslot.isOverlapping(otherSession.timeslot);
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
                && timeslot.equals(otherSession.timeslot)
                && subject.equals(otherSession.subject);
    }

    /**
     * Returns a string representation of the session.
     *
     * @return A formatted string with session ID, timeslot, and subject.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", id)
                .add("timeslot", timeslot)
                .add("subject", subject)
                .toString();
    }
}

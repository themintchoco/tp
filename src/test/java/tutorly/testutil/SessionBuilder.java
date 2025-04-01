package tutorly.testutil;

import java.time.LocalDateTime;

import tutorly.model.session.Session;
import tutorly.model.session.Subject;
import tutorly.model.session.Timeslot;

/**
 * A utility class to help with building Session objects.
 */
public class SessionBuilder {

    public static final Timeslot DEFAULT_TIMESLOT = new Timeslot(LocalDateTime.of(2025, 3, 25, 10, 0),
            LocalDateTime.of(2025, 3, 25, 12, 0));
    public static final Subject DEFAULT_SUBJECT = new Subject("Math");

    private int id;
    private Timeslot timeslot;
    private Subject subject;

    /**
     * Creates a {@code SessionBuilder} with the default details.
     */
    public SessionBuilder() {
        id = 0;
        timeslot = DEFAULT_TIMESLOT;
        subject = DEFAULT_SUBJECT;
    }

    /**
     * Initializes the SessionBuilder with the data of {@code sessionToCopy}.
     */
    public SessionBuilder(Session sessionToCopy) {
        id = sessionToCopy.getId();
        timeslot = sessionToCopy.getTimeslot();
        subject = sessionToCopy.getSubject();
    }

    /**
     * Sets the {@code id} of the {@code Session} that we are building.
     */
    public SessionBuilder withId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the {@code timeslot} of the {@code Session} that we are building.
     */
    public SessionBuilder withTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
        return this;
    }

    /**
     * Sets the {@code subject} of the {@code Session} that we are building.
     */
    public SessionBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }

    /**
     * Builds a {@code Session} with the given details.
     */
    public Session build() {
        Session session = new Session(timeslot, subject);
        if (id > 0) {
            session.setId(id);
        }
        return session;
    }

}

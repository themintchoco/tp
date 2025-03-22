package tutorly.testutil;

import java.time.LocalDate;

import tutorly.model.session.Session;

/**
 * A utility class to help with building Session objects.
 */
public class SessionBuilder {

    public static final LocalDate DEFAULT_DATE = LocalDate.of(2020, 1, 1);
    public static final String DEFAULT_SUBJECT = "Math";

    private int id;
    private LocalDate date;
    private String subject;

    /**
     * Creates a {@code SessionBuilder} with the default details.
     */
    public SessionBuilder() {
        id = 0;
        date = DEFAULT_DATE;
        subject = DEFAULT_SUBJECT;
    }

    /**
     * Initializes the SessionBuilder with the data of {@code sessionToCopy}.
     */
    public SessionBuilder(Session sessionToCopy) {
        id = sessionToCopy.getId();
        date = sessionToCopy.getDate();
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
     * Sets the {@code date} of the {@code Session} that we are building.
     */
    public SessionBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code subject} of the {@code Session} that we are building.
     */
    public SessionBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Session build() {
        return new Session(id, date, subject);
    }

}

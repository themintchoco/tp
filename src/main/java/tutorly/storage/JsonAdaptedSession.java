package tutorly.storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tutorly.commons.exceptions.IllegalValueException;
import tutorly.model.session.Session;

/**
 * Jackson-friendly version of {@link Session}.
 */
class JsonAdaptedSession {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Session's %s field is missing!";

    private final int sessionId;
    private final String date;
    private final String subject;

    /**
     * Constructs a {@code JsonAdaptedSession} with the given session details.
     */
    @JsonCreator
    public JsonAdaptedSession(@JsonProperty("sessionId") int sessionId,
                              @JsonProperty("date") String date,
                              @JsonProperty("subject") String subject) {
        this.sessionId = sessionId;
        this.date = date;
        this.subject = subject;
    }

    /**
     * Converts a given {@code Session} into this class for Jackson use.
     */
    public JsonAdaptedSession(Session source) {
        this.sessionId = source.getSessionId();
        this.date = source.getDate().toString();
        this.subject = source.getSubject();
    }

    /**
     * Converts this Jackson-friendly adapted session object into the model's {@code Session} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted session.
     */
    public Session toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }

        LocalDate modelDate;
        try {
            modelDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Invalid date format. Expected format: YYYY-MM-DD");
        }

        if (subject == null || subject.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "subject"));
        }

        if (sessionId < 1) {
            throw new IllegalValueException("Session ID must be a positive integer.");
        }

        return new Session(sessionId, modelDate, subject);
    }
}

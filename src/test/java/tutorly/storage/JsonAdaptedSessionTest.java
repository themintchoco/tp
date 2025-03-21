package tutorly.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutorly.storage.JsonAdaptedSession.MISSING_FIELD_MESSAGE_FORMAT;
import static tutorly.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import tutorly.commons.exceptions.IllegalValueException;
import tutorly.model.session.Session;

public class JsonAdaptedSessionTest {
    private static final int VALID_SESSION_ID = 1;
    private static final String VALID_DATE = "2025-03-21";
    private static final String VALID_SUBJECT = "Mathematics";

    private static final int INVALID_SESSION_ID = 0;
    private static final String INVALID_DATE = "21-03-2025"; // Incorrect format
    private static final String INVALID_SUBJECT = ""; // Empty subject

    @Test
    public void toModelType_validSessionDetails_returnsSession() throws Exception {
        JsonAdaptedSession session = new JsonAdaptedSession(new Session(VALID_SESSION_ID, LocalDate.parse(VALID_DATE), VALID_SUBJECT));
        assertEquals(new Session(VALID_SESSION_ID, LocalDate.parse(VALID_DATE), VALID_SUBJECT), session.toModelType());
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(VALID_SESSION_ID, null, VALID_SUBJECT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "date");
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_invalidDateFormat_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(VALID_SESSION_ID, INVALID_DATE, VALID_SUBJECT);
        String expectedMessage = "Invalid date format. Expected format: YYYY-MM-DD";
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_nullSubject_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(VALID_SESSION_ID, VALID_DATE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "subject");
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_emptySubject_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(VALID_SESSION_ID, VALID_DATE, INVALID_SUBJECT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "subject");
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }

    @Test
    public void toModelType_invalidSessionId_throwsIllegalValueException() {
        JsonAdaptedSession session = new JsonAdaptedSession(INVALID_SESSION_ID, VALID_DATE, VALID_SUBJECT);
        String expectedMessage = "Session ID must be a positive integer.";
        assertThrows(IllegalValueException.class, expectedMessage, session::toModelType);
    }
}

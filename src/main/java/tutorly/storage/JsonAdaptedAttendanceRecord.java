package tutorly.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tutorly.commons.exceptions.IllegalValueException;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.attendancerecord.Feedback;

/**
 * Jackson-friendly version of {@link AttendanceRecord}.
 */
public class JsonAdaptedAttendanceRecord {

    private final int studentId;
    private final int sessionId;
    private final boolean isPresent;
    private final String feedback;

    /**
     * Constructs a {@code JsonAdaptedAttendanceRecord} with the given attendance record details.
     */
    @JsonCreator
    public JsonAdaptedAttendanceRecord(@JsonProperty("studentId") int studentId,
                                       @JsonProperty("sessionId") int sessionId,
                                       @JsonProperty("isPresent") boolean isPresent,
                                       @JsonProperty("feedback") String feedback) {
        this.studentId = studentId;
        this.sessionId = sessionId;
        this.isPresent = isPresent;
        this.feedback = feedback;
    }

    /**
     * Converts a given {@code AttendanceRecord} into this class for Jackson use.
     */
    public JsonAdaptedAttendanceRecord(AttendanceRecord source) {
        studentId = source.getStudentId();
        sessionId = source.getSessionId();
        isPresent = source.getAttendance();
        feedback = source.getFeedback().value;
    }

    /**
     * Converts this Jackson-friendly adapted attendace record object into the model's {@code AttendanceRecord} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted attendance record.
     */
    public AttendanceRecord toModelType() throws IllegalValueException {
        if (studentId <= 0 || sessionId <= 0) {
            throw new IllegalValueException(AttendanceRecord.MESSAGE_CONSTRAINTS);
        }

        final Feedback modelFeedback;
        if (feedback == null || feedback.isEmpty()) {
            modelFeedback = Feedback.empty();
        } else if (!Feedback.isValidFeedback(feedback)) {
            throw new IllegalValueException(Feedback.MESSAGE_CONSTRAINTS);
        } else {
            modelFeedback = new Feedback(feedback);
        }

        return new AttendanceRecord(studentId, sessionId, isPresent, modelFeedback);
    }

}

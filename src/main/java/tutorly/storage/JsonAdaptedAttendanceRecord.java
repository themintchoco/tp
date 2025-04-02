package tutorly.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tutorly.commons.exceptions.IllegalValueException;
import tutorly.model.attendancerecord.AttendanceRecord;

/**
 * Jackson-friendly version of {@link AttendanceRecord}.
 */
public class JsonAdaptedAttendanceRecord {

    private final long studentId;
    private final long sessionId;
    private final boolean isPresent;

    /**
     * Constructs a {@code JsonAdaptedAttendanceRecord} with the given attendance record details.
     */
    @JsonCreator
    public JsonAdaptedAttendanceRecord(@JsonProperty("studentId") long studentId,
            @JsonProperty("sessionId") long sessionId,
            @JsonProperty("isPresent") boolean isPresent) {
        this.studentId = studentId;
        this.sessionId = sessionId;
        this.isPresent = isPresent;
    }

    /**
     * Converts a given {@code AttendanceRecord} into this class for Jackson use.
     */
    public JsonAdaptedAttendanceRecord(AttendanceRecord source) {
        studentId = source.getStudentId();
        sessionId = source.getSessionId();
        isPresent = source.getAttendance();
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

        return new AttendanceRecord(studentId, sessionId, isPresent);
    }

}

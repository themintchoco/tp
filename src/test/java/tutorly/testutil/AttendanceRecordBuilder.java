package tutorly.testutil;

import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.person.Person;

/**
 * A utility class to help with building AttendanceRecord objects.
 */
public class AttendanceRecordBuilder {

    private int studentId;
    private int sessionId;
    private boolean isPresent;

    /**
     * Creates a {@code AttendanceRecordBuilder} with the default details.
     */
    public AttendanceRecordBuilder() {
        studentId = 0;
        sessionId = 0;
        isPresent = false;
    }

    /**
     * Initializes the AttendanceRecordBuilder with the data of {@code attendanceRecordToCopy}.
     */
    public AttendanceRecordBuilder(tutorly.model.attendancerecord.AttendanceRecord attendanceRecordToCopy) {
        studentId = attendanceRecordToCopy.getStudentId();
        sessionId = attendanceRecordToCopy.getSessionId();
        isPresent = attendanceRecordToCopy.getAttendance();
    }

    /**
     * Sets the {@code studentId} of the {@code AttendanceRecord} that we are building.
     */
    public AttendanceRecordBuilder withStudentId(int studentId) {
        this.studentId = studentId;
        return this;
    }

    /**
     * Sets the {@code studentId} of the {@code AttendanceRecord} that we are building.
     */
    public AttendanceRecordBuilder withPerson(Person person) {
        return withStudentId(person.getId());
    }

    /**
     * Sets the {@code sessionId} of the {@code AttendanceRecord} that we are building.
     */
    public AttendanceRecordBuilder withSessionId(int sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    /**
     * Sets the {@code sessionId} of the {@code AttendanceRecord} that we are building.
     */
    public AttendanceRecordBuilder withSession(tutorly.model.session.Session session) {
        return withSessionId(session.getId());
    }

    /**
     * Sets the {@code isPresent} of the {@code AttendanceRecord} that we are building.
     */
    public AttendanceRecordBuilder withIsPresent(boolean isPresent) {
        this.isPresent = isPresent;
        return this;
    }

    public AttendanceRecord build() {
        return new AttendanceRecord(studentId, sessionId, isPresent);
    }

}

package tutorly.model.attendancerecord;

import tutorly.commons.util.ToStringBuilder;
import tutorly.model.Session;
import tutorly.model.person.Person;

/**
 * Represents the attendance record of a student.
 */
public class AttendanceRecord {
    private Person student;
    private Session session;
    private boolean isPresent;

    /**
     * Constructs a new AttendanceRecord.
     *
     * @param student The student whose attendance is being recorded.
     * @param session The session for which attendance is being recorded.
     * @param isPresent Whether the student is present for the session.
     */
    public AttendanceRecord(Person student, Session session, boolean isPresent) {
        this.student = student;
        this.session = session;
        this.isPresent = isPresent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("student", student)
                .add("session", session)
                .add("isPresent", isPresent)
                .toString();
    }
}

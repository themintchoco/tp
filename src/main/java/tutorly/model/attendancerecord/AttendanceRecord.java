package tutorly.model.attendancerecord;

import static java.util.Objects.requireNonNull;

import tutorly.commons.util.ToStringBuilder;
import tutorly.model.session.Session;
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
        requireNonNull(student);
        requireNonNull(session);

        this.student = student;
        this.session = session;
        this.isPresent = isPresent;
    }

    /**
     * Sets the attendance status of the student.
     *
     * @param isPresent The attendance status of the student.
     */
    public void setAttendance(boolean isPresent) {
        this.isPresent = isPresent;
    }

    public Person getStudent() {
        return student;
    }

    public Session getSession() {
        return session;
    }

    public boolean getAttendance() {
        return isPresent;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AttendanceRecord)) {
            return false;
        }

        AttendanceRecord otherRecord = (AttendanceRecord) other;
        return student.equals(otherRecord.student)
                && session.equals(otherRecord.session)
                && isPresent == otherRecord.isPresent;
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

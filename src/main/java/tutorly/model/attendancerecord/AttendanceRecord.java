package tutorly.model.attendancerecord;

import tutorly.commons.util.ToStringBuilder;

/**
 * Represents the attendance record of a student.
 */
public class AttendanceRecord {

    public static final String MESSAGE_CONSTRAINTS = "Attenance record must have a valid student ID and session ID.";

    private int studentId;
    private int sessionId;
    private boolean isPresent;

    /**
     * Constructs a new AttendanceRecord.
     *
     * @param studentId The ID of the student whose attendance is being recorded.
     * @param sessionId The ID of the session for which attendance is being recorded.
     * @param isPresent Whether the student is present for the session.
     */
    public AttendanceRecord(int studentId, int sessionId, boolean isPresent) {
        this.studentId = studentId;
        this.sessionId = sessionId;
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

    public int getStudentId() {
        return studentId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public boolean getAttendance() {
        return isPresent;
    }

    /**
     * Returns true if the record is for the same student and session.
     * This defines a weaker notion of equality between two attendance records.
     */
    public boolean isSameRecord(AttendanceRecord otherRecord) {
        if (otherRecord == this) {
            return true;
        }

        return otherRecord != null
                && studentId == otherRecord.studentId
                && sessionId == otherRecord.sessionId;
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
        return studentId == otherRecord.studentId
                && sessionId == otherRecord.sessionId
                && isPresent == otherRecord.isPresent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentId", studentId)
                .add("sessionId", sessionId)
                .add("isPresent", isPresent)
                .toString();
    }
}

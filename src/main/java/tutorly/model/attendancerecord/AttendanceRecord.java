package tutorly.model.attendancerecord;

import static java.util.Objects.requireNonNull;

import tutorly.commons.util.ToStringBuilder;

/**
 * Represents the attendance record of a student.
 */
public class AttendanceRecord {

    public static final String MESSAGE_CONSTRAINTS = "Attendance record must have a valid student ID and session ID.";

    private int studentId;
    private int sessionId;
    private boolean isPresent;
    private Feedback feedback;

    /**
     * Constructs a new AttendanceRecord.
     *
     * @param studentId The ID of the student whose attendance is being recorded.
     * @param sessionId The ID of the session for which attendance is being recorded.
     * @param isPresent Whether the student is present for the session.
     * @param feedback The feedback given by the student for the session.
     */
    public AttendanceRecord(int studentId, int sessionId, boolean isPresent, Feedback feedback) {
        requireNonNull(feedback);
        this.studentId = studentId;
        this.sessionId = sessionId;
        this.isPresent = isPresent;
        this.feedback = feedback;
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

    public Feedback getFeedback() {
        return feedback;
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
                && isPresent == otherRecord.isPresent
                && feedback.equals(otherRecord.feedback);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentId", studentId)
                .add("sessionId", sessionId)
                .add("isPresent", isPresent)
                .add("feedback", feedback)
                .toString();
    }
}

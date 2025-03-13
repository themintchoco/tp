package tutorly.model;
import java.time.LocalDate;

/**
 * Represents a tutoring session for a student.
 */
public class Session {
    private int studentId;  // Unique identifier for the student
    private LocalDate date; // Date of the session

    /**
     * Constructs a new Session.
     *
     * @param studentId The unique identifier of the student.
     * @param date The date of the session.
     */
    public Session(int studentId, LocalDate date) {
        this.studentId = studentId;
        this.date = date;
    }

    /**
     * Gets the student ID associated with this session.
     *
     * @return The student ID.
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Gets the session date.
     *
     * @return The date of the session.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns a string representation of the session.
     *
     * @return A formatted string with student ID and session date.
     */
    @Override
    public String toString() {
        return "Session{studentId=" + studentId + ", date=" + date + "}";
    }
}

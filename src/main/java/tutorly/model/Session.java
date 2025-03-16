package tutorly.model;
import java.time.LocalDate;

/**
 * Represents a tutoring session for a student.
 */
public class Session {
    private static int nextSessionId = 1;
    private int studentId;
    private LocalDate date;
    private int sessionId;
    private String subject;

    /**
     * Constructs a new Session with an auto-incremented session ID.
     *
     * @param studentId The unique identifier of the student.
     * @param date The date of the session.
     */
    public Session(int studentId, LocalDate date, String subject) {
        this.studentId = studentId;
        this.date = date;
        this.sessionId = nextSessionId++;
        this.subject = subject;
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
     * Gets the session ID.
     *
     * @return The session ID.
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     * Gets the subject of the session.
     *
     * @return The subject of the session.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Returns a string representation of the session.
     *
     * @return A formatted string with student ID, session ID, date, and subject.
     */
    @Override
    public String toString() {
        return "Session{studentId=" + studentId + ", sessionId="
                + sessionId + ", date=" + date + ", subject=" + subject + "}";
    }
}

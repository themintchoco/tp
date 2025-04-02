package tutorly.model.attendancerecord;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import tutorly.model.person.Person;
import tutorly.model.session.Session;
import tutorly.testutil.PersonBuilder;
import tutorly.testutil.SessionBuilder;

public class AttendanceRecordTest {
    private final Person student = new PersonBuilder().build();
    private final Session session = new SessionBuilder().withId(1).build();

    @Test
    public void getStudent() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(),
                false, Feedback.empty());
        assertEquals(attendanceRecord.getStudentId(), student.getId());
    }

    @Test
    public void getSession() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(),
                false, Feedback.empty());
        assertEquals(attendanceRecord.getSessionId(), session.getId());
    }

    @Test
    public void getAttendance() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(),
                false, Feedback.empty());
        assertEquals(attendanceRecord.getAttendance(), false);
    }

    @Test
    public void getFeedback() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(),
                false, new Feedback("Feedback"));
        assertEquals(attendanceRecord.getFeedback(), new Feedback("Feedback"));
    }

    @Test
    public void equals() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(),
                false, Feedback.empty());
        AttendanceRecord attendanceRecordCopy = new AttendanceRecord(student.getId(), session.getId(),
                true, Feedback.empty());
        assert !attendanceRecord.equals(attendanceRecordCopy);
    }

    @Test
    public void equals_true() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(),
                false, new Feedback("Feedback"));
        AttendanceRecord attendanceRecordCopy = new AttendanceRecord(student.getId(), session.getId(),
                false, new Feedback("Feedback"));
        assert attendanceRecord.equals(attendanceRecordCopy);
    }

    @Test
    public void equals_sameObject() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(),
                false, Feedback.empty());
        assert attendanceRecord.equals(attendanceRecord);
    }

    @Test
    public void equals_differentObject() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(),
                false, Feedback.empty());
        assert !attendanceRecord.equals(new Object());
    }

    @Test
    public void toStringMethod() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(),
                false, new Feedback("Feedback"));
        String expected = AttendanceRecord.class.getCanonicalName() + "{studentId=" + student.getId()
                + ", sessionId=" + session.getId()
                + ", isPresent=" + false
                + ", feedback=" + "Feedback" + "}";
        assert attendanceRecord.toString().equals(expected);
    }
}

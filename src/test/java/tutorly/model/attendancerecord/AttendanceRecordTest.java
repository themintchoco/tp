package tutorly.model.attendancerecord;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import tutorly.model.person.Person;
import tutorly.model.session.Session;
import tutorly.testutil.PersonBuilder;

public class AttendanceRecordTest {
    private final Person student = new PersonBuilder().build();
    private final Session session = new Session(1, null, "Math");

    @Test
    public void getStudent() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(), false);
        assertEquals(attendanceRecord.getStudentId(), student.getId());
    }

    @Test
    public void getSession() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(), false);
        assertEquals(attendanceRecord.getSessionId(), session.getId());
    }

    @Test
    public void getAttendance() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(), false);
        assertEquals(attendanceRecord.getAttendance(), false);
    }

    @Test
    public void getFeedback() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(), false);
        assertEquals(attendanceRecord.getFeedback(), "");
    }

    @Test
    public void setFeedback() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(), false);
        attendanceRecord.setFeedback("Good job!");
        assertEquals(attendanceRecord.getFeedback(), "Good job!");
    }

    @Test
    public void equals() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(), false);
        AttendanceRecord attendanceRecordCopy = new AttendanceRecord(student.getId(), session.getId(), true);
        assert !attendanceRecord.equals(attendanceRecordCopy);
    }

    @Test
    public void equals_sameObject() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(), false);
        assert attendanceRecord.equals(attendanceRecord);
    }

    @Test
    public void equals_differentObject() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(), false);
        assert !attendanceRecord.equals(new Object());
    }

    @Test
    public void toStringMethod() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getId(), false);
        System.out.println(attendanceRecord.toString());
        String expected = AttendanceRecord.class.getCanonicalName() + "{studentId=" + student.getId()
                + ", sessionId=" + session.getId()
                + ", isPresent=" + false
                + ", feedback=" + "" + "}";
        assert attendanceRecord.toString().equals(expected);
    }
}

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
    public void setAttendance() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getSessionId(), false);
        attendanceRecord.setAttendance(true);
        assert attendanceRecord.getAttendance();
    }

    @Test
    public void getStudent() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getSessionId(), false);
        assertEquals(attendanceRecord.getStudentId(), student.getId());
    }

    @Test
    public void getSession() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getSessionId(), false);
        assertEquals(attendanceRecord.getSessionId(), session.getSessionId());
    }

    @Test
    public void equals() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getSessionId(), false);
        AttendanceRecord attendanceRecordCopy = new AttendanceRecord(student.getId(), session.getSessionId(), true);
        assert !attendanceRecord.equals(attendanceRecordCopy);
    }

    @Test
    public void equals_sameObject() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getSessionId(), false);
        assert attendanceRecord.equals(attendanceRecord);
    }

    @Test
    public void equals_differentObject() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getSessionId(), false);
        assert !attendanceRecord.equals(new Object());
    }

    @Test
    public void toStringMethod() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getId(), session.getSessionId(), false);
        System.out.println(attendanceRecord.toString());
        String expected = AttendanceRecord.class.getCanonicalName() + "{studentId=" + student.getId()
                + ", sessionId=" + session.getSessionId()
                + ", isPresent=" + false + "}";
        assert attendanceRecord.toString().equals(expected);
    }
}

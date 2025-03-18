package tutorly.model.attendancerecord;

import static tutorly.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tutorly.model.Session;
import tutorly.model.person.Person;
import tutorly.testutil.PersonBuilder;

public class AttendanceRecordTest {
    private final Person student = new PersonBuilder().build();
    private final Session session = new Session(1, null, "Math");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AttendanceRecord(null, null, false));
    }

    @Test
    public void setAttendance() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student, session, false);
        attendanceRecord.setAttendance(true);
        assert attendanceRecord.getAttendance();
    }

    @Test
    public void getStudent() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student, session, false);
        assert attendanceRecord.getStudent().equals(student);
    }

    @Test
    public void getSession() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student, session, false);
        assert attendanceRecord.getSession().equals(session);
    }
}

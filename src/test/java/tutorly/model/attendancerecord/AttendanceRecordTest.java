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

    @Test
    public void equals() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student, session, false);
        AttendanceRecord attendanceRecordCopy = new AttendanceRecord(student, session, true);
        assert !attendanceRecord.equals(attendanceRecordCopy);
    }

    @Test
    public void equals_sameObject() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student, session, false);
        assert attendanceRecord.equals(attendanceRecord);
    }

    @Test
    public void toStringMethod() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(student, session, false);
        assert attendanceRecord.toString().equals(
                "tutorly.model.attendancerecord.AttendanceRecord{student=tutorly.model.person.Person{id=0, "
                        + "name=Amy Bee, phone=, email=, address=, tags=[], memo=}, session=Session{studentId=1, "
                        + "sessionId=1, date=null, subject=Math}, isPresent=false}");
    }
}

package tutorly.model.attendancerecord;

import tutorly.model.uniquelist.UniqueList;

/**
 * A list of attendance records that enforces uniqueness between its elements and does not allow nulls.
 * An attendance record is considered unique by comparing using {@code AttendanceRecord#isSameRecord(AttendanceRecord)}.
 *
 * @see AttendanceRecord#isSameRecord(AttendanceRecord)
 */
public class UniqueAttendanceRecordList extends UniqueList<AttendanceRecord> {

    @Override
    protected boolean isEquivalent(AttendanceRecord a, AttendanceRecord b) {
        return a.isSameRecord(b);
    }

    @Override
    protected int compare(AttendanceRecord a, AttendanceRecord b) {
        return Long.compare(a.getStudentId(), b.getStudentId());
    }

}
